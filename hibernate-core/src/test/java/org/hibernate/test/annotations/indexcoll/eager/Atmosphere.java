//$Id$
package org.hibernate.test.annotations.indexcoll.eager;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.MapKeyJoinColumns;
import javax.persistence.MapKeyTemporal;
import javax.persistence.TemporalType;

import org.hibernate.test.annotations.indexcoll.Gas;
import org.hibernate.test.annotations.indexcoll.GasKey;

/**
 * @author Emmanuel Bernard
 */
@Entity
public class Atmosphere {

	public static enum Level {
		LOW,
		HIGH
	}

	@Id
	@GeneratedValue
	public Integer id;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKeyColumn(name="gas_name")
	public Map<String, Gas> gases = new HashMap<String, Gas>();

	@MapKeyTemporal(TemporalType.DATE)
	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(nullable=false)
	public Map<Date, String> colorPerDate = new HashMap<Date,String>();

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyEnumerated(EnumType.STRING)
	@MapKeyColumn(nullable=false)
	public Map<Level, String> colorPerLevel = new HashMap<Level,String>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKeyJoinColumn(name="gas_id" )
	@JoinTable(name = "Gas_per_key")
	public Map<GasKey, Gas> gasesPerKey = new HashMap<GasKey, Gas>();

	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name="composition_rate")
	@MapKeyJoinColumns( { @MapKeyJoinColumn(name="gas_id" ) } ) //use @MapKeyJoinColumns explicitly for tests
	@JoinTable(name = "Composition", joinColumns = @JoinColumn(name = "atmosphere_id"))
	public Map<Gas, Double> composition = new HashMap<Gas, Double>();

	//use default JPA 2 column name for map key
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKeyColumn
	@JoinTable(name="Atm_Gas_Def")
	public Map<String, Gas> gasesDef = new HashMap<String, Gas>();

	//use default HAN legacy column name for map key
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKeyColumn
	@JoinTable(name="Atm_Gas_DefLeg")
	public Map<String, Gas> gasesDefLeg = new HashMap<String, Gas>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKeyJoinColumn
	@JoinTable(name = "Gas_p_key_def")
	public Map<GasKey, Gas> gasesPerKeyDef = new HashMap<GasKey, Gas>();

}
