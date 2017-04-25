package xianming.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_dep")
public class Department {
	private int id;
	private String name;
	public Department() {
	}
	
	public Department(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		Department other = (Department) obj;
		if (this.id != other.getId())
			return false;
		return true;
		
	}
	
	public static void main(String[] args) {
		Department d = new Department(1,"财务部");
		Department d2 = new Department(1,"财务部");
		System.out.println(d.equals(d2));
	}
	
	
}
