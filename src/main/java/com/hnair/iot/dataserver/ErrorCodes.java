package com.hnair.iot.dataserver;

/**
 * IAM error codes.
 * 
 * @author Bin.Zhang
 */
public final class ErrorCodes {

	// error code pattern: xxx_xx_xxx -> (HTTP status code) (component) (details)

	// ****************************************************
	// generic error 10_xxx
	// ****************************************************
	public static final int BAD_REQUEST_PARAMS = 400_10_001;
	public static final int INTERNAL_NOT_FOUND = 404_10_001;
	public static final int INTERNAL_SERVER_ERROR = 500_10_001;

	// ****************************************************
	// organization 11_xxx
	// ****************************************************
	public static final int ORGANIZATION_NOT_FOUND = 404_11_001;
	public static final int ORGANIZATION_ALREADY_EXISTS = 409_11_001;
	public static final int ORGANIZATION_NAME_NOT_NULL = 409_11_002;
	public static final int ORGANIZATION_NAME_CONTAINS_SPECIAL_CHARACTERS = 409_11_003;

	// ****************************************************
	// account 12_xxx
	// ****************************************************
	public static final int ACCOUNT_OLD_PASSWORD_MISMATCH = 400_12_001;
	public static final int ACCOUNT_BAD_PASSWORD_STRENGTH = 400_12_002;

	public static final int ACCOUNT_NOT_FOUND = 404_12_001;

	public static final int ACCOUNT_ALREADY_EXISTS = 409_12_001;
	public static final int ACCOUNT_EMAIL_ALREADY_EXISTS = 409_12_002;
	public static final int ACCOUNT_EMAIL_NOT_NULL = 409_12_003;
	public static final int ACCOUNT_USERNAME_NOT_NULL = 409_12_004;
	public static final int ACCOUNT_EMAIL_NOT_FOUND = 409_12_005;
	public static final int ACCOUNT_USERNAME_CONTAINS_SPECIAL_CHARACTERS = 409_12_006;
	public static final int ACCOUNT_PASSWORD_NOT_NULL = 409_12_007;

	// ****************************************************
	// group 13_xxx
	// ****************************************************
	public static final int GROUP_NOT_FOUND = 404_13_001;
	public static final int GROUP_ALREADY_EXISTS = 409_13_001;
	public static final int GROUP_NAME_CONTAINS_SPECIAL_CHARACTERS = 409_13_002;

	// ****************************************************
	// permission 14_xxx
	// ****************************************************
	public static final int PERMISSION_NOT_FOUND = 404_14_001;
	public static final int PERMISSION_ALREADY_EXISTS = 409_14_001;
	public static final int PERMISSION_PERMISSIONNAME_NOT_NULL = 409_14_002;
	public static final int PERMISSION_CONTAINS_SPECIAL_CHARACTERS = 409_14_003;

	// ****************************************************
	// appkey 15_xxx
	// ****************************************************
	public static final int APPKEY_NOT_FOUND = 404_15_001;
	public static final int APPKEY_ALREADY_EXISTS = 409_15_001;
	public static final int APPKEY_IS_EXPIRED = 409_15_002;
}
