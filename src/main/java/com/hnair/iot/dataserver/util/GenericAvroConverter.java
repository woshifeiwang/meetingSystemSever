package com.hnair.iot.dataserver.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonDecoder;
import org.apache.avro.io.JsonEncoder;

/**
 * The Class AvroByteArrayConverter is used to convert {#link org.apache.avro.generic.GenericContainer specific avro
 * records} to/from bytes. NOT Thread safe.
 *
 * @param <T> the generic type that extends GenericContainer
 */
public class GenericAvroConverter<T extends GenericContainer> {
	private static final Charset ENCODING_CHARSET = Charset.forName("UTF-8");
	private static final Charset DECODING_CHARSET = Charset.forName("ISO-8859-1");

	private final Schema schema;
	private final DatumReader<T> datumReader;
	private final DatumWriter<T> datumWriter;
	private BinaryDecoder binaryDecoder;
	private BinaryEncoder binaryEncoder;
	private JsonDecoder jsonDecoder;
	private JsonEncoder jsonEncoder;

	/**
	 * Instantiates a new generic Avro converter.
	 *
	 * @param schemaSrc the schemaSrc
	 */
	public GenericAvroConverter(String schemaSrc) {
		this(new Schema.Parser().parse(schemaSrc));
	}

	/**
	 * Instantiates a new generic avro converter.
	 *
	 * @param schema the schema
	 */
	public GenericAvroConverter(Schema schema) {
		this.schema = schema;
		datumReader = new GenericDatumReader<T>(this.schema);
		datumWriter = new GenericDatumWriter<T>(this.schema);
	}

	/**
	 * Decode binary data.
	 *
	 * @param data the data
	 * @return the decoded object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public T decodeBinary(byte[] data) throws IOException {
		return decodeBinary(data, null);
	}

	/**
	 * Decode binary data.
	 *
	 * @param data the data
	 * @param reuse the reuse
	 * @return the decoded object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public T decodeBinary(byte[] data, T reuse) throws IOException {
		binaryDecoder = DecoderFactory.get().binaryDecoder(data, binaryDecoder);
		return datumReader.read(reuse, binaryDecoder);
	}

	/**
	 * Decode json data.
	 *
	 * @param data the data
	 * @return the decoded object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public T decodeJson(String data) throws IOException {
		return decodeJson(data, null);
	}

	/**
	 * Decode json data.
	 *
	 * @param data the data
	 * @return the decoded object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public T decodeJson(byte[] data) throws IOException {
		return decodeJson(new String(data, DECODING_CHARSET), null);
	}
	static String ROOT = System.getProperty("user.dir") + "/src/main/resources/schemas/";
	
	public static void main(String[] args) {
		String data = "{\"naame\":\"lzq\",\"age\":18}";
		byte[] bytes = data.getBytes();
		Schema.Parser parser = new Schema.Parser().setValidate(true);
		String f = ROOT + "test.avsc";
		String rawSchema = Files.getFileContent(f);
		Schema schema = parser.parse(rawSchema);
		GenericAvroConverter<GenericContainer> genericAvroConverter = new GenericAvroConverter<GenericContainer>(schema);
		try {
			GenericContainer decodeJson = genericAvroConverter.decodeJson(bytes);
			System.out.println(decodeJson.toString());
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Decode json data.
	 *
	 * @param data the data
	 * @param reuse the reuse
	 * @return the decoded object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public T decodeJson(String data, T reuse) throws IOException {
		jsonDecoder = DecoderFactory.get().jsonDecoder(this.schema, data);
		return datumReader.read(null, jsonDecoder);
	}

	/**
	 * Encode record to Json String.
	 *
	 * @param record the object to encode
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String encodeToJson(T record) throws IOException {
		return new String(encodeToJsonBytes(record), ENCODING_CHARSET);
	}

	/**
	 * Encode record to Json and then convert to byte array.
	 *
	 * @param record the object to encode
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public byte[] encodeToJsonBytes(T record) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		jsonEncoder = EncoderFactory.get().jsonEncoder(this.schema, baos);
		datumWriter.write(record, jsonEncoder);
		jsonEncoder.flush();
		baos.flush();
		return baos.toByteArray();
	}

	/**
	 * Encode record to byte array.
	 *
	 * @param record the object to encode
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public byte[] encode(T record) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		binaryEncoder = EncoderFactory.get().binaryEncoder(baos, binaryEncoder);
		datumWriter.write(record, binaryEncoder);
		binaryEncoder.flush();
		baos.flush();
		return baos.toByteArray();
	}

	/**
	 * Convert binary data using schema to Json
	 *
	 * @param rawData the encoded data
	 * @param dataSchema the encoded data schema
	 * @return the string
	 */
	public static String toJson(byte[] rawData, String dataSchema) {
		Schema schema = new Schema.Parser().parse(dataSchema);
		GenericAvroConverter<GenericContainer> converter = new GenericAvroConverter<GenericContainer>(schema);

		try {
			GenericContainer record = converter.decodeBinary(rawData);
			return converter.encodeToJson(record);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Can't parse json data", e); // NOSONAR
		}
	}

	/**
	 * Convert json string using schema to binary data
	 *
	 * @param json the json string
	 * @param dataSchema the encoded data schema
	 * @return the byte[]
	 */
	public static byte[] toRawData(String json, String dataSchema) {
		Schema schema = new Schema.Parser().parse(dataSchema);
		GenericAvroConverter<GenericContainer> converter = new GenericAvroConverter<GenericContainer>(schema);

		try {
			GenericContainer record = converter.decodeJson(json);
			return converter.encode(record);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Can't parse json data", e); // NOSONAR
		}
	}

}
