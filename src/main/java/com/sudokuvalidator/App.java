package com.sudokuvalidator;

import java.io.*;
import java.util.*;

import static java.lang.System.exit;

/**
 * Command line tool (running on jvm) for validating a standard 9x9 Sudoku puzzle.
 * The data is provided by a CSV file.
 *
 */
public class App
{
    private static int boardLength = 9;

    public static void main( String[] args )
    {
        try {
            if (args.length == 0 ) throw new Exception(ErrorMessages.MISSING_FILE_PATH);

            File boardFile = getFile(args[0].trim());
            int[][] board = getBoardFromFile(boardFile);

            isValidSudoku(board);

            exit(0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            exit(1);
        }

    }

    /**
     * Obtains file from disk
     * @param filePath path to the game board file
     * @return file when valid
     * @throws Exception when file is invalid
     */
    private static File getFile(String filePath) throws Exception {
        File boardFile = new File(filePath);

        validateFile(boardFile);

        return boardFile;
    }

    /**
     * Validate file containing the board game
     * @param boardFile game board file
     * @throws Exception if file does not exists
     * @throws Exception if can not read file
     * @throws Exception if file has invalid extension
     */
    private static void validateFile(File boardFile) throws Exception {
        validateFileFormatExtension(boardFile);
        if(!boardFile.exists()) {throw new Exception(ErrorMessages.FILE_DOES_NOT_EXISTS); }
        if(!boardFile.canRead()) { throw new Exception(ErrorMessages.CAN_NOT_READ_FILE); }
    }

    /**
     * Validate file extension. Only .csv files are allowed
     * @param boardFile game board file
     * @throws Exception if file has invalid extension
     */
    private static void validateFileFormatExtension(File boardFile) throws Exception {
        String fileName = boardFile.getName();
        int extensionCharacterPosition = fileName.lastIndexOf('.');
        if (extensionCharacterPosition >= 0) {
            String fileExtension = fileName.substring(extensionCharacterPosition+1);
            if (!fileExtension.trim().toLowerCase().equals("csv")) {
                throw new Exception(ErrorMessages.INVALID_FILE_EXTENSION);
            }
        }
    }

    /**
     * Transforms a comma delimited file into a int[][]
     * @param boardFile game board file
     * @return int[][] containing the game board
     * @throws Exception if board has invalid characters, only numbers from 1 to 9 are accepted
     * @throws Exception if board has a value bellow 1 and/or above 9
     * @throws Exception if board has invalid size, only rows and columns with 9 elements are allowed
     */
    private static int[][] getBoardFromFile(File boardFile) throws Exception {
        int[][] board =  new int [boardLength][boardLength];
        BufferedReader bufRdr  = new BufferedReader(new FileReader(boardFile));
        String line;
        int row = 0;
        int col = 0;

        while((line = bufRdr.readLine()) != null && row < boardLength && col < boardLength)
        {
            StringTokenizer st = new StringTokenizer(line.trim(),",");
            while (st.hasMoreTokens())
            {
                int number;
                try {
                    number = Integer.parseInt(st.nextToken());
                } catch (Exception ex) {
                    throw new Exception(ErrorMessages.INVALID_CHARACTERS);
                }
                if (!(number >= 1 && number <= boardLength)) {
                    throw new Exception(ErrorMessages.ONLY_NUMBERS_FROM_ONE_TO_NINE);
                }

                board[row][col] = number;
                col++;
            }
            if (col < boardLength - 1) {
                throw new Exception(ErrorMessages.INVALID_BOARD_SIZE);
            }
            col = 0;
            row++;
        }

        if (row < boardLength - 1) {
            throw new Exception(ErrorMessages.INVALID_BOARD_SIZE);
        }

        return board;
    }

    /**
     * Validate if a sudoku board is valid
     * @param board game board
     * @throws Exception when solution is invalid
     */
    private static void isValidSudoku(int[][] board) throws Exception{
        for (int i = 0; i < boardLength; i++) {
            int[] row = new int[boardLength];
            int[] square = new int[boardLength];
            int[] column = board[i].clone();

            for (int j = 0; j < boardLength; j ++) {
                row[j] = board[j][i];
                square[j] = board[(i / 3) * 3 + j / 3][i * 3 % boardLength + j % 3];
            }

            if (!(validate(column) && validate(row) && validate(square)))
                throw new Exception(ErrorMessages.INVALID_SOLUTION);
        }
    }

    /**
     * Verify that check @param contains numbers from 1 to 9 only once
     * @param check array containing column, row or inner square of the game board
     * @return true if contains numbers from 1 to 9 only once, and false otherwise
     */
    private static boolean validate(int[] check) {
        int i = 0;
        Arrays.sort(check);
        for (int number : check) {
            if (number != ++i)
                return false;
        }
        return true;
    }
}
