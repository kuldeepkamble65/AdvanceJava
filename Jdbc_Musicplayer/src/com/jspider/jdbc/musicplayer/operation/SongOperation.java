package com.jspider.jdbc.musicplayer.operation;

import java.io.FileReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

import com.jspider.jdbc.musicplayer.Main.MusicPlayer;

public class SongOperation {
private static final Integer String = null;

public static ArrayList<Integer> list = new ArrayList<Integer>();
	
	static SongOperation songOperation = new SongOperation() ;
	
	public static Connection connection;
	public static Statement statement;
	public static PreparedStatement preparedStatement;
	public static ResultSet resultSet;
	public static CallableStatement callableStatement;
	public static FileReader fileReader;
	public static Properties properties = new Properties();
	public static String filePath = "D:\\WEJE2\\Jdbc_Musicplayer\\resources\\db_info.properties";
	public static int result;
	public static String query;
	public static Scanner scanner = new Scanner(System.in);
	
	
	public int countSongs() {
		int count=0;
		try {
			
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("Driverpath"));			
			connection = DriverManager.getConnection(properties.getProperty("db_url"),properties);
			statement = connection.createStatement();
			
			query = "select id from musicplayer";
			
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				list.add(resultSet.getInt(1));
				count++;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return count;
		
	}
	
	public void addSongs() {
		System.out.println("how many songs u want to add");
		int no = scanner.nextInt();
		
		for (int i=1;i<=no;i++) {
			try {								
				fileReader = new FileReader(filePath);
				properties.load(fileReader);
				Class.forName(properties.getProperty("Driverpath"));
				
				connection = DriverManager.getConnection(properties.getProperty("db_url"),properties);
				statement = connection.createStatement();
				
				System.out.println("Enter song id:");			
				int id = scanner.nextInt();
				list.add(id);
				
				System.out.println("Enter song name:");			
				String Name = scanner.next();
				
				
				System.out.println("Enter singer name:");			
				String singer = scanner.next();
				
				
				System.out.println("Enter song length:");			
				double length = scanner.nextDouble();
				
				
				query = "insert into musicplayer values(?,?,?,?)";
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, Name);
				preparedStatement.setString(3, singer);
				preparedStatement.setDouble(4, length);
								
				result = preparedStatement.executeUpdate();
				
				System.out.println("song added successfully");				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MusicPlayer.musicMenu();
	}
	
	public void displayListOfSong() {		
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("Driverpath"));
			
			connection = DriverManager.getConnection(properties.getProperty("db_url"),properties);
			statement = connection.createStatement();
			
			query = "select * from musicplayer";
			
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1)+" "+resultSet.getString(2));
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void removeSong() {
		
		try {
			System.out.println("present songs are...");
			
			songOperation.displayListOfSong();
			
			System.out.println("enter id to delete song");
			int id = scanner.nextInt();
			songOperation.countSongs();
			
			if(list.contains(id)) {
				query = "delete from musicplayer where id="+id;
				
				statement = connection.createStatement();
				result = statement.executeUpdate(query);				
				list.remove(Integer.valueOf(id));
				
				System.out.println("song deleted");
			}
			else {
				System.err.println("enter proper id");
				songOperation.removeSong();
			}
						
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void ChooseSongPlay() {
		try {
			songOperation.displayListOfSong();
			
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("Driverpath"));			
			connection = DriverManager.getConnection(properties.getProperty("db_url"),properties);
			statement = connection.createStatement();
			
			System.out.println("enter id to play song");
			
			int id = scanner.nextInt();
			int count = songOperation.countSongs();
			if(id<=count) {
				query = "select name from musicplayer where id="+id;
				resultSet = statement.executeQuery(query);		
				
				while(resultSet.next()) {
					System.out.println(resultSet.getString(1)+" song playing");
					Thread.sleep(400);
				}
				
			}
			else {
				System.out.println("enter proper id");
				songOperation.ChooseSongPlay();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
				
	public void PlayAllSong() {		
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("Driverpath"));
			
			connection = DriverManager.getConnection(properties.getProperty("db_url"),properties);
			statement = connection.createStatement();
			
			query = "select * from musicplayer";
			
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				
				System.out.println(resultSet.getString(2)+" is playing");
				Thread.sleep(400);
			}
			MusicPlayer.musicMenu();
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void PlayRandom() {		
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("Driverpath"));			
			connection = DriverManager.getConnection(properties.getProperty("db_url"),properties);
			
			query = "select id from musicplayer";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
						
							
			//to get arraylist call the countSong()
			songOperation.countSongs();
			
			//Math.random() * (max - min + 1) + min  
			double id = Math.random() * (list.size());
			query = "select name from musicplayer where id="+list.get((int)id);

			connection = DriverManager.getConnection(properties.getProperty("db_url"),properties);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				System.out.println(resultSet.getString(1)+" is playing");
				Thread.sleep(400);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void update() {
		
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("Driverpath"));
			
			connection = DriverManager.getConnection(properties.getProperty("db_url"),properties);
			statement = connection.createStatement();
			
			//display all song
			System.out.println("present songs are...");
			songOperation.displayListOfSong();
			
			System.out.println("select id of song to update");
			int id = scanner.nextInt();
			int count = songOperation.countSongs();
			
			if(id<=count) {
				
				System.out.println("update followings\n1.name\n2.singer\n3.length\n");	
				int upd = scanner.nextInt();
				
				if(upd<=3) {
					switch (upd) {
						case 1:
							System.out.println("enter new songName");
							String songName = scanner.next();
							query = "update musicplayer set name=? where id="+id;
							
							preparedStatement = connection.prepareStatement(query);
							preparedStatement.setString(1, songName);						
							result = preparedStatement.executeUpdate();						
							
							System.out.println("song update successfully...");						
							
							break;
							
						case 2:
							System.out.println("enter new singer");
							String singerName = scanner.next();
							query = "update musicplayer set singer=? where id="+id+"";
							
							preparedStatement = connection.prepareStatement(query);
							preparedStatement.setString(1, singerName);						
							result = preparedStatement.executeUpdate();						
								
							
							System.out.println("song update successfully...");
							break;
							
						case 3:
							System.out.println("enter new songLength");
							String songLength = scanner.next();
							query = "update musicplayer set length=? where id="+id+"";
							
							preparedStatement = connection.prepareStatement(query);
							preparedStatement.setString(1, songLength);						
							result = preparedStatement.executeUpdate();						
							
							
							System.out.println("song update successfully...");
							
							break;
		
						default:
							System.out.println("invalid input");
							songOperation.update();
							break;
					}
					
				}
				else {
					System.out.println("invalid input");
					songOperation.update();
				}
			}
			else {
				System.err.println("select proper id");
				songOperation.update();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
		
	public void songTable() {
		try {
			fileReader = new FileReader(filePath);
			properties.load(fileReader);
			Class.forName(properties.getProperty("Driverpath"));
			
			connection = DriverManager.getConnection(properties.getProperty("db_url"),properties);
			statement = connection.createStatement();
			
			query = "select * from musicplayer";
			
			resultSet = statement.executeQuery(query);
			System.out.println("*id  *Name      *singer    *length");
			
			int value=0;
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1)+"    "+resultSet.getString(2)+"    "+resultSet.getString(3)+"    "+resultSet.getString(4));
				value++;
			}
			if(value==0) {
				System.out.println("table is empty insert songs");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
