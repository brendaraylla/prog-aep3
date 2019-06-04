package aereo.bilhete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class App {
	
	public static void main(String[] args) {

		try {
			Connection conexão = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/3sadsis2019",
					"postgres",
					"unicesumar");

			criarTabelaBilhete(conexão);			
			excluirTudo(conexão);			
			inserirBilhete(conexão, 1, "ORIGEM", "DESTINO", new Date());
			
			Calendar calendario = Calendar.getInstance();
			calendario.set(Calendar.YEAR, 1975);
			calendario.set(Calendar.MONTH, 1);
			calendario.set(Calendar.DAY_OF_MONTH, 26);
			Date data = calendario.getTime();
			
			inserirBilhete(conexão, 2, "ORIGEM2", "DESTINO2", data);
			
			//alterar data para 11/11/1975
			calendario.set(Calendar.YEAR, 1975);
			calendario.set(Calendar.MONTH, 10);
			calendario.set(Calendar.DAY_OF_MONTH, 11);
			data = calendario.getTime();
			atualizarBilhete(conexão, 2, "ORIGEM NOVO", "DESTINO NOVO", data);
			
			System.out.println("Listando os dados gravados no banco...");
			for (Bilhete c : obterTodosOsBilhetes(conexão)) {
				System.out.println(c.getVoo() + ", " + c.getOrigem() + c.getDestino()
					+ ", " + c.getData().toLocaleString());
			}
						
			System.out.println("Conectou!");
			conexão.close();
			System.out.println("Fechou!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Fim.");
	}
	
	public static List<Bilhete> obterTodosOsBilhetes(Connection conexão) throws Exception {
		List<Bilhete> bilhetes = new ArrayList<Bilhete>();
		
		String sql = "select id, nome, nascimento from cliente";
		Statement statement = conexão.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			Integer voo = resultSet.getInt("voo");
			String origem = resultSet.getString("origem");
			String destino = resultSet.getString("destino");
			Date data = new java.util.Date(resultSet.getDate("data").getTime());
			
			Bilhete bilhete = new Bilhete(voo, origem, destino, data);
			bilhetes.add(bilhete);
		}
		resultSet.close();
		statement.close();
		
		return bilhetes;
	}
	
	
	public static void atualizarBilhete(Connection conexão, Integer voo, String origem, String destino, Date data) throws Exception {
		String sql = "update bilhete "
				+ "set origem = ?, "
				+ "set destino = ?, "
				+ "data = ? "
				+ "where voo = ?";
		PreparedStatement statement = conexão.prepareStatement(sql);
		
		statement.setInt(3, voo);
		statement.setString(1, origem);
		statement.setString(2, destino);
		statement.setDate(4, new java.sql.Date(data.getTime()));
		
		statement.execute();
		statement.close();		
	}
	
	public static void excluirTudo(Connection conexão) throws Exception {
		String sql = "delete from bilhete";		
		Statement statement = conexão.createStatement();
		statement.execute(sql);
		statement.close();
	}
	
	public static void inserirBilhete(Connection conexão, Integer voo, String origem, String destino, Date data) throws Exception {
		String sql = "insert into bilhete (voo, origem, destino, data) values (?,?,?,?)";
		PreparedStatement statement = conexão.prepareStatement(sql);
		
		statement.setInt(1, voo);
		statement.setString(2, origem);
		statement.setString(3, destino);
		statement.setDate(4, new java.sql.Date(data.getTime()));
		
		statement.execute();
		statement.close();
	}
	
	public static void criarTabelaBilhete(Connection conexão) throws Exception {
		String sql = "create table if not exists bilhete ( "
				+ "voo integer not null primary key, " 
				+ "origem varchar(255) not null, "
				+ "destino varchar(255) not null, "
				+ "data date not null "
				+ ")";		
		Statement statement = conexão.createStatement();
		statement.execute(sql);
		statement.close();
	}

}
