package org.playwrightji.salesforce.login;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class Register {

    private static final String FIRST_NAME = "First name";
    private static final String LAST_NAME = "Last name";
    private static final String JOB_TITLE = "Job title";
    private static final String EMAIL = "Email";
    private static final String PHONE = "Phone";
    private static final String COMPANY = "Company";
    private static final String EMPLOYEES = "Employees";
    private static final String COUNTRY = "Country/Region";
    private static final String COMPANY_LANGUAGE = "Company Language";

    public Register(Page page, String firstname, String lastname, String jobTitle, String email, String phone, String company,
                    Country country, CompanySize companySize, CompanyLanguage companyLanguage) {

        page.getByLabel(FIRST_NAME).fill(firstname);
        page.getByLabel(LAST_NAME).fill(lastname);
        page.getByLabel(JOB_TITLE).fill(jobTitle);
        page.getByLabel(EMAIL).fill(email);
        page.getByLabel(PHONE).fill(phone);
        page.getByPlaceholder(COMPANY).fill(company);
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName(EMPLOYEES)).selectOption(companySize.getEmployees());
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName(COUNTRY)).selectOption(country.getCountry());
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName(COMPANY_LANGUAGE)).selectOption(companyLanguage.getLanguage());
    }
    public enum CompanySize {
        XXS("3"),
        XS("15"),
        S("50"),
        M("150"),
        L("475"),
        XL("1315"),
        XXL("2500");

        private String code;

        CompanySize(String code) {
            this.code = code;
        }

        public String getEmployees() {
            return code;
        }
    }

    public enum CompanyLanguage {
        ENGLISH("en_US"),
        FRENCH("fr"),
        DUTCH("nl_NL"),
        ITALIAN("it");

        private String code;

        CompanyLanguage(String code) {
            this.code = code;
        }

        public String getLanguage() {
            return code;
        }
    }

    public enum Country {
        UNITED_STATES("US"),
        AFGHANISTAN("AF"),
        ALBANIA("AL"),
        ALGERIA("DZ"),
        AUSTRALIA("AU");

        private String code;

        Country(String code) {
            this.code = code;
        }

        public String getCountry() {
            return code;
        }
    }
}
