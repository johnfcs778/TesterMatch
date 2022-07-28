package org.example.Utilities;

import com.opencsv.exceptions.CsvValidationException;
import org.example.Dao;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface to make Data Reader flexible to other implementations for reading data
 */
public interface IDataReader {
    public Dao readData(String dataFolderPath) throws IOException, CsvValidationException;
}
