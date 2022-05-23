import javax.persistence.*;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class MyHibernateDemo {

//    private DataSource getDataSource() {
//        final MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setDatabaseName("backpack");
//        dataSource.setUser("slleer");
//        dataSource.setPassword("AntraToaster0!Cart");
//        dataSource.setUrl("jdbc:mysql//localhost:3306/");
//        return dataSource;
//    }

    private Properties getProperties() {
        final Properties properties = new Properties();
        properties.setProperty("javax.persistence.jdbc.user", "slleer");
        properties.setProperty("javax.persistence.jdbc.password", "AntraToaster0!Cart");
        properties.setProperty("javax.persistence.jdbc.url", "jdbc:mysql//localhost:3306/backpack");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        // possible source of issue, if problem here change key to: javax.persistence.jdbc.driver
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        return properties;
    }

    private EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("Hibernate-Demo", this.getProperties());
    }
    private static void insertPlayer(EntityManager em, String name, String uName) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Player player = new Player();
        player.setUsername(uName);
        player.setName(name);
        em.persist(player);
        tx.commit();
    }
    private static void insertPlayer(EntityManager em, Player player) {
        em.getTransaction().begin();
        em.persist(player);
        em.getTransaction().commit();
    }
    private static void getPlayerById(EntityManager em, String id) {
        Player player = em.find(Player.class, id);
        System.out.println(player);
    }
    private static void getPlayerByName(EntityManager em, String name) {
        Query query = em.createQuery("select p from Player p where p.name = ?1");
        query.setParameter(1, name);
        Player p = (Player)query.getSingleResult();
        System.out.println(p);
    }
    private static void updatePlayerNameById(EntityManager em, String p_id, String name) {
        em.getTransaction().begin();
        Player player = em.getReference(Player.class, p_id);
        player.setName(name);
        em.getTransaction().commit();
    }
    private static void deletePlayerById(EntityManager em, String p_id) {
        Player player = em.find(Player.class, p_id);
        deleteCharactersByPlayer(em, player);
        em.getTransaction().begin();
        em.remove(player);
        em.getTransaction().commit();
    }
    private static void insertCharacterForExistingPlayer(EntityManager em, String uName, String charName) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Player p_id = em.find(Player.class, uName);
        PlayerCharacter pc = new PlayerCharacter();
        pc.setCharName(charName);
        pc.setPlayer_ID(p_id);
        em.persist(pc);
        tx.commit();
    }
    private static void insertCharacterForExistingPlayer(EntityManager em, Player player, String charName) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        PlayerCharacter pc = new PlayerCharacter();
        pc.setCharName(charName);
        pc.setPlayer_ID(player);
        em.persist(pc);
        tx.commit();
    }
    private static void getCharactersByPlayer(Player player) {
        List<PlayerCharacter> pcList = player.getPlayerCharacterList();
        for (PlayerCharacter playerCharacter : pcList) {
            System.out.println(playerCharacter);
        }
    }
    private static PlayerCharacter getCharacterByCharName(EntityManager em, String charName, Player player) {
        Query query = em.createQuery("select c from PlayerCharacter c where c.charName = ?1 and c.player_ID = ?2");
        query.setParameter(1, charName);
        query.setParameter(2, player);
        return (PlayerCharacter) query.getSingleResult();
    }
    private static void getCharactersByPlayerId(EntityManager em, String p_id) {
        Player player = em.find(Player.class, p_id);
        getCharactersByPlayer(player);
    }
    private static void updatePlayerCharacterCharNameById(EntityManager em, String id, String charName) {
        PlayerCharacter pc = em.find(PlayerCharacter.class, id);
        em.getTransaction().begin();
        pc.setCharName(charName);
        em.getTransaction().commit();
    }
    private static void deleteCharacterByCharName(EntityManager em, String charName) {
        em.getTransaction().begin();
        Query query = em.createQuery("select c from PlayerCharacter c where c.charName = ?1");
        query.setParameter(1, charName);
        PlayerCharacter pc = (PlayerCharacter) query.getSingleResult();
        em.remove(pc);
        em.getTransaction().commit();
    }
    private static void deleteCharactersByPlayer(EntityManager em, Player player) {
        em.getTransaction().begin();
        Player p = em.find(Player.class, player.getUsername());
        Iterator<PlayerCharacter> charItr = p.getPlayerCharacterList().iterator();
        while(charItr.hasNext()) {
            PlayerCharacter pc = charItr.next();
            Iterator<CharacterItem> itmItr = pc.getCharacterItemList().iterator();
            while(itmItr.hasNext()) {
                itmItr.remove();
            }
            charItr.remove();
        }
        em.getTransaction().commit();
    }
    private static void insertItem(EntityManager em, Item item) {
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
    }
    private static void insertItem(EntityManager em, String name, boolean consumable, boolean attunementNeeded, String description) {
        Item item = new Item();
        item.setName(name);
        item.setConsumable(consumable);
        item.setAttunement(attunementNeeded);
        item.setDescription(description);
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
    }
    private static Item getItemByName(EntityManager em, String name) {
        Query query = em.createQuery("select i from Item i where i.name = ?1");
        query.setParameter(1, name);
        Item item = (Item) query.getSingleResult();
        System.out.println(item);
        return item;
    }
    private static void getItemById(EntityManager em, int id) {
        Item item = em.find(Item.class, id);
        System.out.println(item);
    }
    private static void updateItemConsumableById(EntityManager em, int id, boolean consumable) {
        em.getTransaction().begin();
        Item item = em.getReference(Item.class, id);
        item.setConsumable(consumable);
        em.persist(item);
        em.getTransaction().commit();
    }
    private static void updateItemAttunementById(EntityManager em, int id, boolean attunement) {
        em.getTransaction().begin();
        Item item = em.getReference(Item.class, id);
        item.setAttunement(attunement);
        em.persist(item);
        em.getTransaction().commit();
    }
    private static void updateItemDescriptionById(EntityManager em, int id, String description) {
        em.getTransaction().begin();
        Item item = em.getReference(Item.class, id);
        item.setDescription(description);
        em.persist(item);
        em.getTransaction().commit();
    }
    private static void insertCharacterItem(EntityManager em, PlayerCharacter pc, Item item) {
        em.getTransaction().begin();
        CharacterItem charItem = new CharacterItem(pc, item);
        charItem.setCount(1);
        charItem.setAttuned(false);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void insertCharacterItem(EntityManager em, PlayerCharacter pc, Item item, int count) {
        em.getTransaction().begin();
        CharacterItem charItem = new CharacterItem(pc, item);
        charItem.setCount(count);
        charItem.setAttuned(false);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void insertCharacterItem(EntityManager em, PlayerCharacter pc, Item item, boolean attuned) {
        em.getTransaction().begin();
        CharacterItem charItem = new CharacterItem(pc, item);
        charItem.setCount(1);
        charItem.setAttuned(attuned);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void insertCharacterItem(EntityManager em, PlayerCharacter pc, Item item, int count, boolean attuned) {
        em.getTransaction().begin();
        CharacterItem charItem = new CharacterItem(pc, item);
        charItem.setCount(count);
        charItem.setAttuned(attuned);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void updateCharacterItemCountById(EntityManager em, int id, int count) {
        em.getTransaction().begin();
        CharacterItem charItem = em.getReference(CharacterItem.class, id);
        charItem.setCount(count);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void updateCharacterItemAttunementById(EntityManager em, int id, boolean attuned) {
        em.getTransaction().begin();
        CharacterItem charItem = em.getReference(CharacterItem.class, id);
        charItem.setAttuned(attuned);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void updateCharacterItemIncrementCountById(EntityManager em, int id, int count) {
        em.getTransaction().begin();
        CharacterItem charItem = em.getReference(CharacterItem.class, id);
        charItem.setCount(charItem.getCount()+count);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void updateCharacterItemDecrementCountById(EntityManager em, int id, int count) {
        em.getTransaction().begin();
        CharacterItem charItem = em.getReference(CharacterItem.class, id);
        int val = charItem.getCount() - count;
        charItem.decrementCount(count);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void deleteCharacterItemById(EntityManager em, int id) {
        em.getTransaction().begin();
        CharacterItem characterItem = em.find(CharacterItem.class, id);
        em.remove(characterItem);
        em.getTransaction().commit();
    }

    public static void main(String[] args) {
        MyHibernateDemo demo = new MyHibernateDemo();
        Properties properties = demo.getProperties();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Hibernate-Demo");
        EntityManager em = emf.createEntityManager();
        Player player1 = new Player();
        player1.setUsername("usr1");
        player1.setName("name1");
        insertPlayer(em, player1);
        insertPlayer(em, "usr2", "name2");
        insertCharacterForExistingPlayer(em, "usr2", "char1");
        insertCharacterForExistingPlayer(em, "usr2", "char2");
        insertCharacterForExistingPlayer(em, "usr2", "char2");
        insertCharacterForExistingPlayer(em, player1, "char1");
        insertCharacterForExistingPlayer(em, player1, "char2");
        insertCharacterForExistingPlayer(em, player1, "char3");
        insertItem(em, "item1", true, false, "this is a consumable un-attuned item");
        Item item = new Item();
        item.setName("item2");
        item.setConsumable(false);
        item.setAttunement(true);
        item.setDescription("this is a non consumable attunable item");
        insertItem(em, item);
        insertItem(em,  "item3", false, false, "this is a non consumable un-attuned item");
        Item item1 = getItemByName(em, "item1");
        Item item2 = getItemByName(em, "item2");
        Item item3 = getItemByName(em, "item3");
        PlayerCharacter pc1 = getCharacterByCharName(em, "char1", player1);
        PlayerCharacter pc2 = getCharacterByCharName(em, "char2", player1);



    }



}
