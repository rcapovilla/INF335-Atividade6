package atividade6;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import static com.mongodb.client.model.Filters.eq;

public class MongoAppMain {
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Conectando com o MongoDB");

		//MongoClient client = MongoClients.create("mongodb://mongoAdmin:ihatepasswords@127.0.0.4:27017/?authSource=<authenticationDb>");
		MongoClient client = MongoClients.create("mongodb://172.17.0.4:27017");
		System.out.println("Conectando a base test");
		MongoDatabase db = client.getDatabase("test");
		System.out.println("Lista as colecoes da base test");
		Iterable<Document> collections = db.listCollections();
		for (Document col: collections) {
			System.out.println(col.get("name"));
		}
		
		MongoCollection<Document> collection = db.getCollection("produtos");

		System.out.println("Imprimindo Produtos");
		Iterable<Document> produtos = collection.find();
		
		
		//Inserir nova linha no Banco de Dados
		collection.insertOne(new Document()
				//.append("_id", new Object())
				.append("nome", "Prod7")
				.append("descricao", "Bla bla")
				.append("valor", "500.0")
				.append("estado", "Bla bla"));
				
		System.out.println("Lista com novo produto");
		produtos = collection.find();
		for (Document produto: produtos) {
			String nome = produto.getString("nome");
			String descricao = produto.getString("descricao");
			String valor = produto.getString("valor");
			String estado = produto.getString("estado");
			System.out.println(nome + " -- "
			+ descricao + " -- "
			+ valor + " -- "
			+ estado);
		}
		
		//Alterar o valor de um produto
		Document doc = collection.find(eq("nome","Prod7")).first();
		Bson updates = Updates.set("valor", "400.0");
		
		collection.updateOne(doc, updates);
		
		System.out.println("Lista com valor do produto alterado");
		produtos = collection.find();
		for (Document produto: produtos) {
			String nome = produto.getString("nome");
			String descricao = produto.getString("descricao");
			String valor = produto.getString("valor");
			String estado = produto.getString("estado");
			System.out.println(nome + " -- "
			+ descricao + " -- "
			+ valor + " -- "
			+ estado);
		}
		
		//remover item do Banco de dados
		
		Bson removeQuery = eq("nome","Prod7");
		collection.deleteOne(removeQuery);
		
		System.out.println("Lista Original");
		produtos = collection.find();
		for (Document produto: produtos) {
			String nome = produto.getString("nome");
			String descricao = produto.getString("descricao");
			String valor = produto.getString("valor");
			String estado = produto.getString("estado");
			System.out.println(nome + " -- "
			+ descricao + " -- "
			+ valor + " -- "
			+ estado);
		}	
	}	
}
