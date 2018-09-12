// Randy Jackson - n01173844 - Programming Project #1 - Problem 1

import java.util.Random;

public class produceConsume 
{

	public static void main(String[] args) throws InterruptedException
	{
		produceAndConsume pAndC = new produceAndConsume();
		
		Thread producer = new Thread(new Runnable()
				{

					@Override
					public void run() 
					{
						try 
						{
							pAndC.produce();
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}	
					}
	
				});
		
		Thread consumer = new Thread(new Runnable()
				{
		
					@Override
					public void run() 
					{
						try 
						{
							pAndC.consume();
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}	
					}
		
				});
		
		producer.start();
		consumer.start();
		
		producer.join();
		consumer.join();

	}



	public static class produceAndConsume
	{
		Integer[] numbers = new Integer[2];
		Random rand = new Random();
		
		public void produce() throws InterruptedException
		{
			while(true)
			{
				synchronized(this)
				{
					while(numbers[0] != null)
					{
						wait();	//waits until array elements are null
					}
					
					System.out.print("[Filling the array with: ");
					
					//takes charge of buffer and sets elements to randomly generated ints
					
					for(int i = 0; i < numbers.length; i++)
					{
						numbers[i] = rand.nextInt(100);
						System.out.print(numbers[i] + " ");
					}
					
					System.out.print("]\n");
					
					notify();
					
					Thread.sleep(500);
					
				}
			}
			
		}
		public void consume() throws InterruptedException
		{
			while(true)
			{
				synchronized(this)
				{
					while(numbers[0] == null)
					{
						wait(); //waits until array elements are not null
					}
					
					System.out.println("[Emptying Array]");
					
					//takes charge of buffer and sets elements to null
					
					for(int i = 0; i < numbers.length; i++)
					{
						numbers[i] = null;
					}

					notify();
					
					Thread.sleep(500);
				}
			}
		}
	}

}
