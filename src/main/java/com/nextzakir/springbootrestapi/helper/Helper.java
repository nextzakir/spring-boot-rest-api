package com.nextzakir.springbootrestapi.helper;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

public class Helper {

    // method to generate a random integer of 6 digits
    public static Integer getSixDigitRandomInteger() {

        Random rand = new Random();
        int low = 100000; // this value is inclusive
        int high = 1000000; // this value is exclusive
        return rand.nextInt(high-low) + low;

    }
	
	// method to generate a random complex string of length n
    public static String getComplexString(int n) {
    	
        // chose a Character random from this String 
        String AlphaNumericString = "!@#$%^&*?"
                					+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
  
        return sb.toString(); 
    }
	
	// method to generate a random alpha numeric string of length n
    public static String getAlphaNumericString(int n) {
    	
        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
  
        return sb.toString(); 
    }
	  
    // method to generate a unique string
    public static String getUniqueString() { 
    	
    	// set string length
    	int n = 19;
    	
        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
                          .charAt(index));
        } 
  
        return sb.toString() + getCurrentTimestamp().getTime(); 
    }
    
    // method to get current timestamp
    public static Timestamp getCurrentTimestamp() {
		Calendar calendar = Calendar.getInstance();
        return new Timestamp(calendar.getTimeInMillis());
	}

    public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8), URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }
        return query_pairs;
    }

    public static String buildPageUri(Pageable page) {
        return fromUriString("").query("page={page}&size={size}")
                .buildAndExpand(page.getPageNumber(), page.getPageSize()).toUriString();
    }

    public static Boolean isValidKeyword(String str) {
        String s = "^[a-zA-Z]*$"; // Ac
        Pattern p = Pattern.compile(s);

        if (str == null) {
            return false;
        }

        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static Boolean isAlphaNumeric(String str) {
        String s = "^[a-z0-9]+$";
        Pattern p = Pattern.compile(s);

        if (str == null) {
            return false;
        }

        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static Boolean isValidEmail(String email) {
        String s = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern p = Pattern.compile(s);

        if (email == null) {
            return false;
        }

        return p.matcher(email).matches();
    }

    // This are the methods to handle communicating with external API
//    public static Mono<? extends Throwable> handle4xxAnd5xxError(ClientResponse clientResponse) {
//        Mono<String> errorMessage = clientResponse.bodyToMono(String.class);
//
//        return errorMessage.flatMap((message) -> {
//            if (clientResponse.statusCode().is4xxClientError()) {
//                try {
//                    CustomErrorHandler errorHandler = new ObjectMapper().readValue(message, CustomErrorHandler.class);
//                    throw new BadRequestException(errorHandler.getMessage());
//                } catch (JsonProcessingException e) {
//                    System.out.println("Error in parsing String to JSON!");
//                    throw new InternalServerErrorException("Something weird happened on the server!");
//                }
//            } else {
//                try {
//                    CustomErrorHandler errorHandler = new ObjectMapper().readValue(message, CustomErrorHandler.class);
//                    throw new InternalServerErrorException(errorHandler.getMessage());
//                } catch (JsonProcessingException e) {
//                    System.out.println("Error in parsing String to JSON!");
//                    throw new InternalServerErrorException("Something weird happened on the server!");
//                }
//            }
//        });
//    }
    // The End

    public static String encodeURLComponent(String component) {
        return URLEncoder.encode(component, StandardCharsets.UTF_8);
    }

    public static String queryStringFromPageable(Pageable p) {
        StringBuilder ans = new StringBuilder();
        ans.append("page=");
        ans.append(encodeURLComponent(p.getPageNumber() + ""));
        ans.append("&size=");
        ans.append(encodeURLComponent(p.getPageSize() + ""));

        // No sorting
        if (p.getSort() == null)
            return ans.toString();

        // Sorting is specified
        for (Sort.Order o : p.getSort()) {
            ans.append("&sort=");
            ans.append(encodeURLComponent(o.getProperty()));
            ans.append(",");
            ans.append(encodeURLComponent(o.getDirection().name()));
        }

        return ans.toString();
    }

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        String noWhiteSpace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

}