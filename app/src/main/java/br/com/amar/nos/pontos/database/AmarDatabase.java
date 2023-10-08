package br.com.amar.nos.pontos.database;


//@Database(entities = { Contrato.class }, version = 1, exportSchema = false)
//public abstract class AmarDatabase extends RoomDatabase {
public abstract class AmarDatabase {

    public static final String NOME_BANCO_DE_DADOS = "amar-nos-pontos.db";

//    public static AmarDatabase getInstance(Context context) {
//        return Room.databaseBuilder(context, AmarDatabase.class, NOME_BANCO_DE_DADOS)
//                .allowMainThreadQueries()
//                .build();
//    }
}
