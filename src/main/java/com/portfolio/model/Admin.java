package com.portfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Admin_Table")
@Getter
@Setter
@Data

public class Admin {
	@Id
 String email;
 String pass;
}
