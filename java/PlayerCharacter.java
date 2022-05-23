import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "p_character")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "charName")
    private String charName;

    @OneToOne
    @JoinColumn(name = "player_ID", referencedColumnName = "id")
    private Player player_ID;


    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CharacterItem> characterItemList = new ArrayList<>();

    public List<CharacterItem> getCharacterItemList() {
        return characterItemList;
    }

    public void setCharacterItemList(List<CharacterItem> characterItemList) {
        this.characterItemList = characterItemList;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public Player getPlayer_ID() {
        return player_ID;
    }

    public void setPlayer_ID(Player player) {
        this.player_ID = player;
    }

    public void addCharacterItem(CharacterItem charItem) {
        this.characterItemList.add(charItem);
    }
    @Override
    public String toString() {
        return "PlayerCharacter{" +
                "id='" + id + '\'' +
                ", charName='" + charName + '\'' +
                ", player_ID=" + player_ID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerCharacter)) return false;
        PlayerCharacter that = (PlayerCharacter) o;
        return id == that.id && charName.equals(that.charName) && player_ID.equals(that.player_ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
