import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "player")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "name")
    private String name;


    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlayerCharacter> playerCharacterList = new ArrayList<>();

    public List<PlayerCharacter> getPlayerCharacterList() {
        return playerCharacterList;
    }

    public void setPlayerCharacterList(List<PlayerCharacter> playerCharacterList) {
        this.playerCharacterList = playerCharacterList;
    }

    public void addPlayerCharacter(PlayerCharacter pc) {
        this.playerCharacterList.add(pc);
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", playerCharacterList=" + playerCharacterList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return username.equals(player.username) && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
