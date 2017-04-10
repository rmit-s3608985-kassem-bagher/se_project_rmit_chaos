package se_project_rmit_chaos;

public class Employee
{
   String name;
   String username;
   String password;

   Employee(String name, String username, String password)
   {
      this.name = name;
      this.username = username;
      this.password = password;
   }
   public String getName() { return name; }
   public String getUsername() { return username; }
   public String getPassword() { return password; }
}

