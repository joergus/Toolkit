package de.wwservices.util.url;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class URLSplitter {

    public static void prettyPrintURL(String url) {
        int indexOfQ = url.indexOf("?");
        String baseURL = url.substring(0, indexOfQ);
        String suffixURL = url.substring(indexOfQ + 1, url.length());
        StringTokenizer paramTokens = new StringTokenizer(suffixURL, "&");
        Map<String, String> params = new TreeMap<String, String>();
        while (paramTokens.hasMoreTokens()) {
            String nextToken = paramTokens.nextToken();
            String[] split = nextToken.split("=");
            if (split.length > 1) {
                params.put(split[0], split[1]);
            } else {
                params.put(split[0], "");
            }
        }
        prettyPrintMap(params);
    }

    public static void main(String[] args) {
        readAndPrintFromSystemClipboard();

    }

    private static void readAndPrintFromSystemClipboard() {
        Clipboard systemClipboard = Toolkit.getDefaultToolkit()
                .getSystemClipboard();
        try {
            Object data = systemClipboard.getData(DataFlavor
                    .selectBestTextFlavor(systemClipboard
                            .getAvailableDataFlavors()));
            if (data instanceof String) {
                prettyPrintURL(data.toString());
            } else
                System.out.println("Type is " + data.getClass());
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void prettyPrintMap(Map<?, ?> map) {
        for (Entry<?, ?> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
