package com.payconiq.apitests;

import java.util.ResourceBundle;

/**
 * This class provides common utility functions used across the tests.
 * * */
public final class Utilities {

        /**
         * Private constructor to prevent initialization of this class.
         * * */
        private Utilities() {
                // Prevent creating objects for this class.
        }

        /**
         * This methods returns the string property for the passed key.
         * @param key for the property
         * @return Value of the property.
         * */
        public static String getProperty(String key) {
                ResourceBundle resourceBundle = ResourceBundle.
                        getBundle("system");
                return resourceBundle.getString(key);
        }
}
