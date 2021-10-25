package models.types;

public class EncryptedPassportElement {
    public String type;
    public String data;
    public String phone_number;
    public String email;
    public PassportFile[] files;
    public PassportFile front_side;
    public PassportFile reverse_side;
    public PassportFile selfie;
    public PassportFile[] translation;
    public String hash;
}
