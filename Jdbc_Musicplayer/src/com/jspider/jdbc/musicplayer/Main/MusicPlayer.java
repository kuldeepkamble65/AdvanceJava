package com.jspider.jdbc.musicplayer.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.jspider.jdbc.musicplayer.operation.SongOperation;

public class MusicPlayer {
	static boolean loop = true;
	static Scanner scanner = new Scanner(System.in);
	
	static SongOperation songOperation = new SongOperation();
	
	public static void main(String[] args) {
		musicMenu();
		while (loop) {
			musicMenu();
		}		
		try {
			if(SongOperation.connection!=null) {
				SongOperation.connection.close();
			}
			if(SongOperation.statement!=null) {
				SongOperation.statement.close();
			}
			if(SongOperation.callableStatement!=null) {
				SongOperation.callableStatement.close();
			}
			if(SongOperation.preparedStatement!=null) {
				SongOperation.preparedStatement.close();
			}
			if(SongOperation.resultSet!=null) {
				SongOperation.resultSet.close();
			}
			if(SongOperation.fileReader!=null) { 
				SongOperation.fileReader.close();
			}
			if(SongOperation.scanner!=null) {
				SongOperation.scanner.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void musicMenu() {
		
		System.out.println("Menu:-\n1.show Song-Table\n2.play \n3.Add/Remove \n4.Edit \n5.Exit");
		System.err.println("choose option :-");
		int key = scanner.nextInt();
		
		switch (key) {
		case 1:
			songOperation.songTable();
			loop = false;
			musicMenu();
			break;
		case 2: {
			System.out.println("1.choose song \n2.play all songs \n3.random song \n4.go back.\n");
			System.err.println("choose option :-");
			int key1 = scanner.nextInt();
			
			switch (key1) {
				case 1: {
					songOperation.ChooseSongPlay();
					break;
				}
				case 2: {
					songOperation.PlayAllSong();
					break;
				}
				case 3: {
					songOperation.PlayRandom();
					break;
				}
				case 4: {
					musicMenu();
					break;
				}
				default :
					System.out.println("invalid input");
					musicMenu();
					break;
				}
			break;
		}
		case 3: {
			System.out.println("1.Add song \n2.Remove song \n3. go back");
			System.err.println("choose option :-");
			int key2 = scanner.nextInt();
			switch (key2) {
				case 1: {
					songOperation.addSongs();
					break;
				}
				case 2: {
					songOperation.removeSong();
					break;
				}
				case 3: {
					loop = false;
					musicMenu();
					break;
				}
				default:
					System.out.println("invalid input");
			}
			break;
		}
		case 4: {
			songOperation.update();
			loop = false;
			musicMenu();
			break;
		}
		case 5:
			loop=false;
			break;
		default : {
			System.out.println("invalid input");			
		}
		}	
	}
}
