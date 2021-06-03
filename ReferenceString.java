import java.io.*;
import java.util.*;

class ReplacementAlgorithm
{
	void insert(int pageFrame, int pages[])
	{
	}

	boolean doesNotExist(int number, int array[])
	{
		for(int element : array)
		{
			if(element == number)
			{
				return false;
			}
		}
		return true;
	}
	
}

class LRU extends ReplacementAlgorithm
{

	int NULL = 999;

	int searchIndex(int number, int array[])
	{
		int key = NULL;
		for(int index = 0; index < array.length; index++)
		{
			if(number == array[index])
			{
				key = index;
				break;
			}
		}
		return (key);
	}


	@Override
	void insert(int pageFrame, int pages[])
	{
		int pageFaults = 0, entry = 0, lruIndex = 0;
		int queue[] = new int[pageFrame], queueIndex = 0;
		for(queueIndex = 0; queueIndex < queue.length; queueIndex++)
			queue[queueIndex] = NULL;
		queueIndex = 0;
		Deque<Integer> deque = new LinkedList<>();
		for(int index = 0; index < pages.length; index++)
		{
			if(entry < pageFrame && super.doesNotExist(pages[index], queue))
			{
				queue[queueIndex] = pages[index];
				deque.add(pages[index]);
				queueIndex++;
				pageFaults++;
				entry++;
			}
			else if (entry == pageFrame && super.doesNotExist(pages[index], queue))
			{
				queue[lruIndex] = pages[index];
				deque.remove();
				deque.add(pages[index]);
				lruIndex = searchIndex(deque.peek(), queue); 
				pageFaults++;
			}
			else if (entry == pageFrame && (!super.doesNotExist(pages[index], queue)))
			{
				deque.remove(pages[index]);
				deque.add(pages[index]);
				lruIndex = searchIndex(deque.peek(), queue); 
			}
		}
		System.out.println("LRU Page Faults = " + pageFaults);	
	}
	
}

class FIFO extends ReplacementAlgorithm
{
	int NULL = 999;

	@Override
	void insert(int pageFrame, int pages[])
	{
		int pageFaults = 0, oldestPageIndex = 0, entry = 0;
		int queue[] = new int[pageFrame], queueIndex = 0;
		for(queueIndex = 0; queueIndex < queue.length; queueIndex++)
			queue[queueIndex] = NULL;
		queueIndex = 0;
		for(int index = 0; index < pages.length; index++)
		{
			if(entry < pageFrame && super.doesNotExist(pages[index], queue))
			{
				queue[queueIndex] = pages[index];
				queueIndex++;
				pageFaults++;
				entry++;
			}
			else if (entry == pageFrame && super.doesNotExist(pages[index], queue))
			{
				if((oldestPageIndex + 1) == pageFrame)
				{
					queue[oldestPageIndex] = pages[index];
					oldestPageIndex = 0;
				}
				else
				{
					queue[oldestPageIndex] = pages[index];
					oldestPageIndex++;
				}
				pageFaults++;
			}
		}
		System.out.println("FIFO Page Faults = " + pageFaults);
	}	
}

class ReferenceString
{
	public static void main(String args[])
	{
		int pageFrameLRU, pageFrameFIFO, index, LRUlength, FIFOlength;
		LRU lru = new LRU();
		FIFO fifo = new FIFO();
		Scanner input = new Scanner(System.in);

		System.out.println("Enter Page Frame for LRU: ");
		pageFrameLRU = input.nextInt();
		System.out.println("Enter Reference String length: ");
		LRUlength = input.nextInt();
		int LRUpages[] = new int[LRUlength];
		System.out.println("Enter Reference String for LRU: ");
		for(index = 0; index < LRUlength; index++)
			LRUpages[index] = input.nextInt();
	
		System.out.println("Enter Page Frame for FIFO: ");
		pageFrameFIFO = input.nextInt();
		System.out.println("Enter Reference String length: ");
		FIFOlength = input.nextInt();
		int FIFOpages[] = new int[FIFOlength];
		System.out.println("Enter Reference String for FIFO: ");
		for(index = 0; index < FIFOlength; index++)
			FIFOpages[index] = input.nextInt();


		lru.insert(pageFrameLRU, LRUpages);
		fifo.insert(pageFrameFIFO, FIFOpages);
	}
}