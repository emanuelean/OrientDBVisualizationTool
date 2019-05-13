package com.mycompany.orientdbvisualizationtool.database;
import com.tinkerpop.blueprints.Vertex;

public class OrganizationAttributes {
	private String id;
    private String tel;
    private String uname;
    private String email;
    private String pwd;
    private String jobtitle;
    private String mobile;
    private String lastname;
    private String firstname;
    private String role;
    private String name;
    private String workinghoursstart;
    private Boolean consumption;
    private String shortname;
    private String workinghoursend;
    private String oid;
    private Boolean enviroment;
    private Boolean savings;
    private Boolean publicdashboards;
    private Boolean generation; 
    
    
    /**
    * Creates an object with the attributes of the organization
    *
    * @param v the vertex to get the attributes from
    */
   public OrganizationAttributes(Vertex v) {
       try {
           this.id = v.getProperty("id");
       } catch (Exception e) {
           this.id = null;
       }
       try {
           this.tel = v.getProperty("area");
       } catch (Exception e) {
           this.tel = null;
       }
       try {
           this.uname = v.getProperty("username");
       } catch (Exception e) {
           this.uname = null;
       }
       try {
           this.email = v.getProperty("email");
       } catch (Exception e) {
           this.email = null;
       }
       try {
           this.pwd = v.getProperty("password");
       } catch (Exception e) {
           this.pwd = null;
       }
       try {
           this.jobtitle = v.getProperty("job-title");
       } catch (Exception e) {
           this.jobtitle = null;
       }
       try {
           this.mobile = v.getProperty("mobile");
       } catch (Exception e) {
           this.mobile = null;
       }
       try {
           this.firstname = v.getProperty("first-name");
       } catch (Exception e) {
           this.firstname = null;
       }
       try {
           this.lastname = v.getProperty("last-name");
       } catch (Exception e) {
           this.lastname = null;
       }
       try {
           this.role = v.getProperty("role");
       } catch (Exception e) {
           this.role = null;
       }
       try {
           this.name = v.getProperty("name");
       } catch (Exception e) {
           this.name = null;
       }
       try {
           this.workinghoursstart = v.getProperty("working-hours-start");
       } catch (Exception e) {
           this.workinghoursstart = null;
       }
       try {
           this.consumption = v.getProperty("consumption");
       } catch (Exception e) {
           this.consumption = false;
       }
       try {
           this.shortname = v.getProperty("short");
       } catch (Exception e) {
           this.shortname = null;
       }
       try {
           this.workinghoursend = v.getProperty("working-hours-end");
       } catch (Exception e) {
           this.workinghoursend = null;
       }
       try {
           this.oid = v.getProperty("oid");
       } catch (Exception e) {
           this.oid = null;
       }
       try {
           this.enviroment = v.getProperty("enviroment");
       } catch (Exception e) {
           this.enviroment = false;
       }
       try {
           this.savings = v.getProperty("savings-plan");
       } catch (Exception e) {
           this.savings = false;
       }
       try {
           this.publicdashboards = v.getProperty("public-dashboards");
       } catch (Exception e) {
           this.publicdashboards = false;
       }
       try {
           this.generation = v.getProperty("generation");
       } catch (Exception e) {
           this.generation = false;
       }
   }
    
    
    
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @return the jobtitle
	 */
	public String getJobtitle() {
		return jobtitle;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the workinghoursstart
	 */
	public String getWorkinghoursstart() {
		return workinghoursstart;
	}

	/**
	 * @return the consumption
	 */
	public Boolean getConsumption() {
		return consumption;
	}

	/**
	 * @return the shortname
	 */
	public String getShortname() {
		return shortname;
	}

	/**
	 * @return the workinghoursend
	 */
	public String getWorkinghoursend() {
		return workinghoursend;
	}

	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @return the enviroment
	 */
	public Boolean getEnviroment() {
		return enviroment;
	}

	/**
	 * @return the savings
	 */
	public Boolean getSavings() {
		return savings;
	}

	/**
	 * @return the publicdashboards
	 */
	public Boolean getPublicdashboards() {
		return publicdashboards;
	}

	/**
	 * @return the generation
	 */
	public Boolean getGeneration() {
		return generation;
	}


}
