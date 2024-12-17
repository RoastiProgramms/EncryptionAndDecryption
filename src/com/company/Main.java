package com.company;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static String encrypt(String plainText, String key) {
        int blockSize = key.length(); // Block size is determined by the key length

        // Pad the plain text with spaces to make its length a multiple of the block size
        plainText = padWithSpaces(plainText, blockSize);

        // Split the text into blocks
        List<String> blocks = splitIntoBlocks(plainText, blockSize);

        // Rearrange each block based on the key
        List<String> rearrangedBlocks = new ArrayList<>();
        for (String block : blocks) {
            rearrangedBlocks.add(rearrangeBlockWithKey(block, key));
        }

        // Combine the rearranged blocks into cipher text
        return String.join("", rearrangedBlocks);
    }

    // Decrypt the text using block cipher and key logic
    public static String decrypt(String cipherText, String key) {
        int blockSize = key.length(); // Block size is determined by the key length

        // Split the text into blocks
        List<String> blocks = splitIntoBlocks(cipherText, blockSize);

        // Reverse rearrangement of each block based on the key
        List<String> originalBlocks = new ArrayList<>();
        for (String block : blocks) {
            originalBlocks.add(reverseRearrangeBlockWithKey(block, key));
        }

        // Combine the original blocks into plain text
        String plainText = String.join("", originalBlocks);

        // Remove padding spaces
        return plainText.stripTrailing();
    }

    // Pad the text with spaces to ensure the length is a multiple of the block size
    private static String padWithSpaces(String text, int blockSize) {
        int paddingNeeded = blockSize - (text.length() % blockSize);
        if (paddingNeeded == blockSize) {
            return text; // No padding needed
        }
        return text + " ".repeat(paddingNeeded);
    }

    // Rearrange the characters in a block using the key
    private static String rearrangeBlockWithKey(String block, String key) {
        char[] rearranged = new char[block.length()];

        // Rearrange characters based on the key
        for (int i = 0; i < key.length(); i++) {
            int newPosition = key.charAt(i) - '1'; // Convert key digit to 0-based index
            rearranged[newPosition] = block.charAt(i);
        }

        return new String(rearranged);
    }

    // Reverse the rearrangement of characters in a block using the key
    private static String reverseRearrangeBlockWithKey(String block, String key) {
        char[] original = new char[block.length()];

        // Reverse rearrangement based on the key
        for (int i = 0; i < key.length(); i++) {
            int originalPosition = key.charAt(i) - '1'; // Convert key digit to 0-based index
            original[i] = block.charAt(originalPosition);
        }

        return new String(original);
    }

    // Split the text into fixed-size blocks
    private static List<String> splitIntoBlocks(String text, int blockSize) {
        List<String> blocks = new ArrayList<>();
        for (int i = 0; i < text.length(); i += blockSize) {
            int end = Math.min(i + blockSize, text.length());
            blocks.add(text.substring(i, end));
        }
        return blocks;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get inputs from the user
        System.out.print("Enter plain text: ");
        String plainText = scanner.nextLine();

        System.out.print("Enter key (digits only): ");
        String key = scanner.nextLine();

        // Encrypt the text
        String cipherText = encrypt(plainText, key);
        System.out.println("Encrypted text: " + cipherText);

        // Decrypt the text
        String decryptedText = decrypt(cipherText, key);
        System.out.println("Decrypted text: " + decryptedText);
    }
}

