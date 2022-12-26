package com.faystmax.tradingbot.db.entity;

import com.faystmax.tradingbot.db.enums.Role;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 9149997874210856667L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdd;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private Role role;

    @NotNull
    private Boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    private Long telegramChatId;

    @Size(max = 20)
    private String tradingSymbol;

    @Size(max = 200)
    private String binanceApiKey;

    @Size(max = 200)
    private String binanceSecretKey;

    @Size(max = 50)
    private String emailHost;

    @Size(max = 50)
    private String emailUsername;

    @Size(max = 50)
    private String emailPassword;

    @Size(max = 50)
    private String emailFolder;

    private String activeSymbols;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", email='" + email + '\'' +
            '}';
    }
}
