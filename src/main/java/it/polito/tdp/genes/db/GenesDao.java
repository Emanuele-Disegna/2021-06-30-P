package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<String> getVertici(){
		String sql = "SELECT DISTINCT Localization "
				+ "FROM classification";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("Localization"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	public List<Adiacenza> getArchi(){
		String sql = "SELECT L1, L2, COUNT(*) AS peso "
				+ "FROM (SELECT L1, L2, `Type`, COUNT(*) "
				+ "FROM (SELECT DISTINCT c1.Localization AS L1, c2.Localization AS L2, i.GeneID1 AS G1, i.GeneID2 AS G2, i.`Type` "
				+ "FROM classification AS c1, classification AS c2, interactions AS i "
				+ "WHERE (c1.GeneID=i.GeneID1 AND c2.GeneID=i.GeneID2) OR "
				+ "(c2.GeneID=i.GeneID1 AND c1.GeneID=i.GeneID2) AND c1.Localization>c2.Localization) AS p "
				+ "GROUP BY L1, L2,`Type`) AS pp "
				+ "WHERE L1>L2 "
				+ "GROUP BY L1, L2 ";
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(new Adiacenza(res.getString("L1"), res.getString("L2"), res.getInt("peso")));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
}
