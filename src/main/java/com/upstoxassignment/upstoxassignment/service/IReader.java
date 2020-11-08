package com.upstoxassignment.upstoxassignment.service;

/**
 * IReader is interface to use it as reader.
 * Mostly it will be used by Worker 1.
 */
public interface IReader {

    /**
     * To initialize if anythign is required.
     *
     * @param filepath
     */
    public void init(String filepath);

    /**
     * read line
     *
     * @return
     */
    public String readLine();
}
