package com.shop.datasource;

/**
 */
public class DatabaseContextHolder {
    /**
     * The Constant CONTEXTHOLDER.
     */
    private static final ThreadLocal<String> CONTEXTHOLDER = new ThreadLocal<>();

    /**
     * The Constant SESSION_FACTORY_LOCAL.
     */
    public static final String SESSION_FACTORY_LOCAL = "hikariDataSource";

    /**
     * The Constant SESSION_FACTORY_BAIDU.
     */
    public static final String SESSION_FACTORY_BAIDU = "baiduDataSource";

    /**
     * Sets the customer type.
     *
     * @param customerType the new customer type
     */
    public static void setCustomerType(String customerType) {
        CONTEXTHOLDER.set(customerType);
    }

    /**
     * Gets the customer type.
     *
     * @return the customer type
     */
    public static String getCustomerType() {
        return CONTEXTHOLDER.get();
    }

    /**
     * Clear customer type.
     */
    public static void clearCustomerType() {
        CONTEXTHOLDER.remove();
    }
}
