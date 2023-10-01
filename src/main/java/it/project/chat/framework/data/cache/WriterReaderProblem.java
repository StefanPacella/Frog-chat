package it.project.chat.framework.data.cache;

import java.util.concurrent.Semaphore;

public interface WriterReaderProblem <T> {

	void addOneToNWriter();

	int getnWriter();

	void minusOneToNWriter();
	
	Semaphore getWriterSemaphore();
	
	Semaphore getReaderSemaphore();

}
