package model.entities;

public abstract class NguoiDung {
protected String username ;
protected String password ;
protected String hoTen ;
protected String email ;

public NguoiDung(String username, String password , String hoTen , String email){
  this.username=username;
  this.password =password;
  this.email= email;
  this.hoTen=hoTen;
}
public String getEmail() {
    return email;
}
public String getHoTen() {
    return hoTen;
}
public String getPassword() {
    return password;
}
public String getUsername() {
    return username;
}
}
