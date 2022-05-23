import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.*;
import javax.sql.DataSource;

import java.util.List;
import java.util.Properties;

public class MyHibernateDemo {

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
    private static void getPlayerById(EntityManager em, int id) {
        Player player = em.find(Player.class, id);
        System.out.println(player);
    }
    private static void getPlayerByUsername(EntityManager em, String uName) {
        Query query = em.createQuery("select p from Player p where p.username = ?1");
        query.setParameter(1, uName);
        Player p = (Player)query.getSingleResult();
        System.out.println(p);
    }
    private static void updatePlayerNameById(EntityManager em, String p_id, String name) {
        em.getTransaction().begin();
        Player player = em.getReference(Player.class, p_id);
        player.setName(name);
        em.getTransaction().commit();
    }
    private static void deletePlayerById(EntityManager em, int p_id) {
        em.getTransaction().begin();
        Player player = em.find(Player.class, p_id);
        for(PlayerCharacter pc : player.getPlayerCharacterList()) {
            for(CharacterItem charItem : pc.getCharacterItemList()){
                System.out.println(charItem);
                em.remove(charItem);
            }
            System.out.println(pc);
            em.remove(pc);
        }
        System.out.println(player);
        em.remove(player);
        em.getTransaction().commit();
    }
    private static void insertCharacterForExistingPlayer(EntityManager em, int id, String charName) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Player p_id = em.find(Player.class, id);
        PlayerCharacter pc = new PlayerCharacter();
        pc.setCharName(charName);
        pc.setPlayer_ID(p_id);
        em.persist(pc);
        tx.commit();
    }
    private static void insertCharacterForExistingPlayer(EntityManager em, String uName, String charName) {
        Query query = em.createQuery("select p from Player p where p.username = ?1");
        query.setParameter(1, uName);
        Player player = (Player) query.getSingleResult();
        PlayerCharacter pc = new PlayerCharacter();
        pc.setPlayer_ID(player);
        pc.setCharName(charName);
        em.getTransaction().begin();
        em.persist(pc);
        em.getTransaction().commit();
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
    private static void getCharacterListByPlayer(Player player) {
        List<PlayerCharacter> pcList = player.getPlayerCharacterList();
        for (PlayerCharacter playerCharacter : pcList) {
            System.out.println(playerCharacter);
        }
    }
    private static PlayerCharacter getCharacterByPlayerAndCharName(EntityManager em, String charName, Player player) {
        Query query = em.createQuery("select c from PlayerCharacter c where c.charName = ?1 and c.player_ID = ?2");
        query.setParameter(1, charName);
        query.setParameter(2, player);
        return (PlayerCharacter) query.getSingleResult();
    }
    private static void getCharactersByPlayerId(EntityManager em, int p_id) {
        Player player = em.find(Player.class, p_id);
        getCharacterListByPlayer(player);
    }
    private static void updatePlayerCharacterCharNameById(EntityManager em, int id, String charName) {
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
    private static void deleteCharactersByPlayerId(EntityManager em, int id) {
        em.getTransaction().begin();
        Player player = em.find(Player.class, id);
        for(PlayerCharacter pc : player.getPlayerCharacterList()) {
            for(CharacterItem charItem : pc.getCharacterItemList()){
                System.out.println("DELETING: " + charItem);
                em.remove(charItem);
            }
            System.out.println("DELETING: " + pc);
            em.remove(pc);
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
        charItem.setCharacter(pc);
        charItem.setItem(item);
        charItem.setCount(1);
        charItem.setAttuned(false);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void insertCharacterItem(EntityManager em, PlayerCharacter pc, Item item, int count) {
        em.getTransaction().begin();
        CharacterItem charItem = new CharacterItem(pc, item);
        charItem.setCharacter(pc);
        charItem.setItem(item);
        charItem.setCount(count);
        charItem.setAttuned(false);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void insertCharacterItem(EntityManager em, PlayerCharacter pc, Item item, boolean attuned) {
        em.getTransaction().begin();
        CharacterItem charItem = new CharacterItem(pc, item);
        charItem.setCharacter(pc);
        charItem.setItem(item);
        charItem.setCount(1);
        charItem.setAttuned(attuned);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void insertCharacterItem(EntityManager em, PlayerCharacter pc, Item item, int count, boolean attuned) {
        em.getTransaction().begin();
        CharacterItem charItem = new CharacterItem(pc, item);
        charItem.setCharacter(pc);
        charItem.setItem(item);
        charItem.setCount(count);
        charItem.setAttuned(attuned);
        em.persist(charItem);
        em.getTransaction().commit();
    }
    private static void getCharacterItem(EntityManager em, int id) {
        CharacterItem characterItem = em.find(CharacterItem.class, id);
        System.out.println(characterItem);
    }
    private static void getCharacterItem(EntityManager em, String name) {
        Query query = em.createQuery("select c from character_items c where c.name = ?1");
        query.setParameter(1, name);
        System.out.println((CharacterItem)query.getSingleResult());
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
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("xxx");
        System.out.println("hsdlfksjd");
        EntityManager em = entityManagerFactory.createEntityManager();
        PersistenceUnitUtil unitUtil = entityManagerFactory.getPersistenceUnitUtil();
        Player player1 = new Player();
        player1.setUsername("usr1");
        player1.setName("name1");
        insertPlayer(em, player1);
        insertPlayer(em,"name2", "usr2" );
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
        PlayerCharacter pc1 = getCharacterByPlayerAndCharName(em, "char1", player1);
        PlayerCharacter pc2 = getCharacterByPlayerAndCharName(em, "char2", player1);
        PlayerCharacter pc3 = getCharacterByPlayerAndCharName(em, "char3", player1);
        insertCharacterItem(em, pc1, item1);
        insertCharacterItem(em, pc2, item2);
        insertCharacterItem(em, pc2, item3);
        updateCharacterItemIncrementCountById(em, 2, 5);
        getCharacterItem(em, 2);
        getPlayerById(em,1);
        getPlayerByUsername(em, "usr1");
        getCharacterListByPlayer(player1);
        getCharacterByPlayerAndCharName(em, "char1", player1);
        getCharactersByPlayerId(em, 2);
        getItemByName(em, "item2");
        deleteCharactersByPlayerId(em, 1);
        em.close();
        System.exit(0);



    }



}
