package org.example.Utilities;

import com.opencsv.exceptions.CsvValidationException;
import org.example.DataAccess.Dao;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Interface to make Data Reader flexible to other implementations for reading data
 */
public interface IDataReader {
    public Dao readData(String dataFolderPath) throws IOException, CsvValidationException, ExecutionException, InterruptedException;
}
