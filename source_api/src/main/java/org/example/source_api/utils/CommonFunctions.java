package org.example.source_api.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class CommonFunctions {
    // Array of possible first names
    private static String[] firstNames = {"John", "Jane", "Michael", "Emily", "Chris", "Sarah", "David", "Emma", "Daniel", "Sophia"};

    // Array of possible last names
    private static String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};

    // Array of possible salutations
    private static String[] salutations = {"Mr", "Ms"};

    // Array of possible countries
    private static String[] countries = {"USA", "Canada", "Germany", "France", "Japan", "India", "Australia", "Brazil", "UK", "Italy"};

    // Array of possible genders
    private static String[] genders = {"Male", "Female"};

    // Array of possible cities
    private static String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Toronto", "Berlin", "Paris", "Tokyo", "Mumbai"};

    // Array of possible street names
    private static String[] streetNames = {"Main St", "1st Ave", "Maple Dr", "Oak St", "Pine Ln", "Cedar Rd", "Birch St", "Elm Ave", "River Rd", "Sunset Blvd"};

    // Array of possible company names
    private static String[] companyNames = {"TechCorp", "BizSolutions", "Innovatech", "Global Industries", "Cybernetics", "Fintech Inc.", "Health Solutions", "EduWorks", "Green Energy", "Future Enterprises"};

    // Array of possible job titles
    private static String[] jobTitles = {"Software Engineer", "Data Scientist", "Project Manager", "Marketing Director", "Sales Executive", "Product Manager", "Financial Analyst", "HR Manager", "Operations Coordinator", "CEO"};

    private static Map<String, String> countryCodes = populateCountryCodes();

    public int generateRandomInt(int lowerBound, int upperBound) {
        // Create an instance of the Random class
        Random random = new Random();

        // Calculate the range and return a random number within the range
        // (upperBound - lowerBound) gives the total range
        // random.nextInt(range) gives a value from 0 (inclusive) to range (exclusive)
        // Adding lowerBound shifts this range to [lowerBound, upperBound)
        return random.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public int selectRandomInt(int first, int second) {
        // Create an instance of the Random class
        Random random = new Random();
        // Generate a random boolean value
        boolean selectFirst = random.nextBoolean();
        // If true, return the first argument, otherwise return the second
        return selectFirst ? first : second;
    }

    public long generateRandomLong(long lowerBound, long upperBound) {
        // Create an instance of the Random class
        Random random = new Random();

        // Generate a random long value within the specified range
        long range = upperBound - lowerBound;
        long randomLong = (long)(random.nextDouble() * range) + lowerBound;
        return randomLong;
    }

    public double generateRandomDouble(int lowerBound, int upperBound) {
        // Create an instance of the Random class
        Random random = new Random();

        // Generate a random double value within the specified range
        double range = upperBound - lowerBound;
        double randomDouble = random.nextDouble() * range + lowerBound;
        return randomDouble;
    }

    // Method to generate a random first name
    public String generateRandomFirstName() {
        Random random = new Random();
        int index = random.nextInt(firstNames.length);
        return firstNames[index];
    }

    // Method to generate a random last name
    public String generateRandomLastName() {
        Random random = new Random();
        int index = random.nextInt(lastNames.length);
        return lastNames[index];
    }

    // Method to generate a random email
    public String generateRandomEmail() {
        Random random = new Random();
        String firstName = generateRandomFirstName().toLowerCase();
        String lastName = generateRandomLastName().toLowerCase();
        int randomNumber = random.nextInt(1000); // Add a random number to ensure uniqueness
        return firstName + "." + lastName + randomNumber + "@example.com";
    }

    // Method to generate a random salutation
    public String generateRandomSalutation() {
        Random random = new Random();
        int index = random.nextInt(salutations.length);
        return salutations[index];
    }

    // Method to generate a random country
    public String generateRandomCountry() {
        Random random = new Random();
        int index = random.nextInt(countries.length);
        return countries[index];
    }

    // Method to generate a random gender
    public String generateRandomGender() {
        Random random = new Random();
        int index = random.nextInt(genders.length);
        return genders[index];
    }

    // Method to generate a random zip code
    public String generateRandomZipCode() {
        Random random = new Random();
        // Generate a random 5-digit zip code (US style)
        return String.format("%05d", random.nextInt(100000));
    }

    // Method to generate a random phone number
    public String generateRandomPhoneNumber() {
        Random random = new Random();
        // Generate a phone number in the format: (XXX) XXX-XXXX
        int areaCode = random.nextInt(900) + 100;  // 3 digits
        int centralOfficeCode = random.nextInt(900) + 100;  // 3 digits
        int lineNumber = random.nextInt(9000) + 1000;  // 4 digits
        return String.format("(%03d) %03d-%04d", areaCode, centralOfficeCode, lineNumber);
    }

    // Method to generate a random city
    public String generateRandomCity() {
        Random random = new Random();
        int index = random.nextInt(cities.length);
        return cities[index];
    }

    // Method to generate a random street address
    public String generateRandomStreetAddress() {
        Random random = new Random();
        int streetNumber = random.nextInt(9999) + 1;  // Generate street number
        String streetName = streetNames[random.nextInt(streetNames.length)];
        return streetNumber + " " + streetName;
    }

    // Method to generate a random company name
    public String generateRandomCompanyName() {
        Random random = new Random();
        int index = random.nextInt(companyNames.length);
        return companyNames[index];
    }

    // Method to generate a random job title
    public String generateRandomJobTitle() {
        Random random = new Random();
        int index = random.nextInt(jobTitles.length);
        return jobTitles[index];
    }

    // Method to generate a random birthdate in LocalDateTime format
    public LocalDateTime generateRandomBirthDate() {
        Random random = new Random();

        // Set the age range (e.g., between 18 and 100 years old)
        int minAge = 18;
        int maxAge = 100;

        // Generate a random year
        int currentYear = LocalDate.now().getYear();
        int randomYear = currentYear - (random.nextInt(maxAge - minAge) + minAge);

        // Generate a random month
        int randomMonth = random.nextInt(12) + 1;  // Months are 1-12

        // Generate a random day based on the month and year (handle leap years)
        int randomDay = generateRandomDayForMonth(randomYear, randomMonth);

        // Generate random hour, minute, second
        int randomHour = random.nextInt(24);
        int randomMinute = random.nextInt(60);
        int randomSecond = random.nextInt(60);

        // Return the random LocalDateTime
        return LocalDateTime.of(randomYear, randomMonth, randomDay, randomHour, randomMinute, randomSecond);
    }

    public String getCountryCode(String country) {
        return countryCodes.getOrDefault(country, "Unknown");
    }

    // Helper method to generate a valid day for the given year and month
    private static int generateRandomDayForMonth(int year, int month) {
        Random random = new Random();
        int dayInMonth;

        // Check how many days the month has
        switch (Month.of(month)) {
            case FEBRUARY:
                // Check if the year is a leap year
                if (java.time.Year.isLeap(year)) {
                    dayInMonth = random.nextInt(29) + 1;  // 1-29 for leap year
                } else {
                    dayInMonth = random.nextInt(28) + 1;  // 1-28 for non-leap year
                }
                break;
            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER:
                dayInMonth = random.nextInt(30) + 1;  // 1-30 for months with 30 days
                break;
            default:
                dayInMonth = random.nextInt(31) + 1;  // 1-31 for months with 31 days
                break;
        }

        return dayInMonth;
    }

    private static Map<String, String> populateCountryCodes() {
        Map<String, String> countryCodes = new HashMap<>();

        // Populate the map with country names and their codes
        countryCodes.put("USA", "US");
        countryCodes.put("Canada", "CA");
        countryCodes.put("Germany", "DE");
        countryCodes.put("France", "FR");
        countryCodes.put("Japan", "JP");
        countryCodes.put("India", "IN");
        countryCodes.put("Australia", "AU");
        countryCodes.put("Brazil", "BR");
        countryCodes.put("UK", "GB");
        countryCodes.put("Italy", "IT");

        return countryCodes;
    }
}
