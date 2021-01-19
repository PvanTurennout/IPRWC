package nl.pietervanturennout.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ArrayUtils {
    private ArrayUtils() { }

    public static byte[][] chunk(byte[] source, int chunkSize)
    {
        List<byte[]> chunks = new ArrayList<>();
        int start = 0;

        while (start < source.length) {
            int end = Math.min(source.length, start + chunkSize);
            chunks.add(Arrays.copyOfRange(source, start, end));
            start += chunkSize;
        }

        return chunks.toArray(new byte[0][]);
    }

    public static long[][] chunk(long[] source, int chunkSize)
    {
        List<long[]> chunks = new ArrayList<>();
        int start = 0;

        while (start < source.length) {
            int end = Math.min(source.length, start + chunkSize);
            chunks.add(Arrays.copyOfRange(source, start, end));
            start += chunkSize;
        }

        return chunks.toArray(new long[0][]);
    }

    public static <T> boolean contains(T[] arr, Object value)
    {
        for (Object item : arr) {
            if (item.equals(value)) {
                return true;
            }
        }

        return false;
    }

    public static <T> boolean containsAny(T[] arr, Collection values)
    {
        for (Object value : values) {
            if (contains(arr, value)) {
                return true;
            }
        }

        return false;
    }

    public static <T> boolean containsAny(T[] arr, Object[] values)
    {
        for (Object value : Arrays.asList(values)) {
            if (contains(arr, value)) {
                return true;
            }
        }

        return false;
    }

    public static Long[] cast(long[] array) {
        Long[] longs = new Long[array.length];
        for (int i = 0; i < array.length; i++)
            longs[i] = array[i];

        return longs;
    }

    public static long[] listToLongArray(List list) {
        long[] longs = new long[list.size()];

        for (int i = 0; i < longs.length; i++) {
            Object item = list.get(i);

            if (!(item instanceof Long)) {
                throw new IllegalArgumentException("The list does not fully exists of longs");
            }

            longs[i] = (long) item;
        }

        return longs;
    }

    public static <T> ArrayDifferences compare(T[] a, T[] b) {
        return new ArrayDifferences(
                differences(b, a),
                differences(a, b)
        );
    }

    /**
     * Returns a list with the items which are present in array A, but not in array B
     * @param a array to check
     * @param b array with maybe missing items
     * @param <T> Type of array
     * @return The list of missing items
     */
    public static <T> Object[] differences(T[] a, T[] b) {
        List<Object> diffs = new ArrayList<>();

        for (T item : a) {
            if (!contains(b, item)) {
                diffs.add(item);
            }
        }

        return diffs.toArray();
    }

    public static class ArrayDifferences {
        private Object[] missingA;
        private Object[] missingB;

        protected ArrayDifferences(Object[] missingA, Object[] missingB) {
            this.missingA = missingA;
            this.missingB = missingB;
        }

        public Object[] getMissingArrayA() {
            return missingA;
        }

        public Object[] getMissingArrayB() {
            return missingB;
        }
    }
}
