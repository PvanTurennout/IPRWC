package nl.pietervanturennout.utils;

import nl.pietervanturennout.exceptions.InvalidOperationException;

import java.util.*;

public class Headers {
    public static final List<String> singleValueHeaders;

    private Headers() { }

    public static Map<String, Float> parseAccept(String accept) {
        return parseAccept(accept, false);
    }

    public static Map<String, Float> parseAccept(String accept, boolean strict) {
        if (accept == null)
            accept = "";

        accept = accept.trim();
        Map<String, Float> accepts = new HashMap<>();

        if (!accept.isEmpty()) {
            String[] raws = accept.split(",");

            for (String raw : raws) {
                String[] parts = raw.split(";");

                float weight = 1f;
                if (parts.length == 2) {
                    String q = parts[1].trim();

                    if (q.startsWith("q=")) {
                        q = q.substring(2).trim();

                        try {
                            weight = Float.parseFloat(q);

                            if (weight < 0f || weight > 1f) {
                                if (strict)
                                    throw new InvalidOperationException(
                                            "Malformed header; Weight greater then 1.0 or lower then 0.0");

                                weight = Math.max(Math.min(weight, 1f), 0f);
                            }

                        }
                        catch (NumberFormatException e) {
                            if (strict)
                                throw new InvalidOperationException("Malformed header; bad formatted weight (q=)", e);
                        }
                    }
                    else if (strict)
                        throw new InvalidOperationException("Malformed header; invalid parameter (no q= match)");
                }

                accepts.put(parts[0].trim(), weight);
            }
        }

        return accepts;
    }

    public static String bestAcceptMatch(Map<String, Float> accept) {
        return bestAcceptMatch(accept, "*/*");
    }

    public static String bestAcceptMatch(Map<String, Float> accepts, String anyDefault) {
        String highestMime = "*/*";
        float highestWeight = 0f;

        for (Map.Entry<String, Float> accept : accepts.entrySet())
            if (accept.getValue() > highestWeight) {
                highestMime = accept.getKey();
                highestWeight = accept.getValue();
            }

        return highestMime.equals("*/*")
                ? anyDefault
                : highestMime;
    }

    public static String bestAcceptMatch(String accept) {
        return bestAcceptMatch(parseAccept(accept));
    }

    public static String bestAcceptMatch(String accept, String anyDefault) {
        return bestAcceptMatch(parseAccept(accept), anyDefault);
    }

    public static boolean isSingleValueHeader(String header) {
        return singleValueHeaders.contains(header.toLowerCase());
    }

    static {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("content-type");

        singleValueHeaders = Collections.unmodifiableList(headers);
    }
}
