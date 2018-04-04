package io.github.hyuwah.muslimcompanionapp.Model.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ayah {

  @SerializedName("code")
  @Expose
  private Integer code;
  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("data")
  @Expose
  private Data data;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Data getData() {
    return data;
  }

  public void setData(Data data) {
    this.data = data;
  }

  public class Data {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("edition")
    @Expose
    private Edition edition;
    @SerializedName("surah")
    @Expose
    private Surah surah;
    @SerializedName("numberInSurah")
    @Expose
    private Integer numberInSurah;
    @SerializedName("juz")
    @Expose
    private Integer juz;
    @SerializedName("manzil")
    @Expose
    private Integer manzil;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("ruku")
    @Expose
    private Integer ruku;
    @SerializedName("hizbQuarter")
    @Expose
    private Integer hizbQuarter;
    @SerializedName("sajda")
    @Expose
    private Boolean sajda;

    public Integer getNumber() {
      return number;
    }

    public void setNumber(Integer number) {
      this.number = number;
    }

    public String getText() {
      return text;
    }

    public void setText(String text) {
      this.text = text;
    }

    public Edition getEdition() {
      return edition;
    }

    public void setEdition(Edition edition) {
      this.edition = edition;
    }

    public Surah getSurah() {
      return surah;
    }

    public void setSurah(Surah surah) {
      this.surah = surah;
    }

    public Integer getNumberInSurah() {
      return numberInSurah;
    }

    public void setNumberInSurah(Integer numberInSurah) {
      this.numberInSurah = numberInSurah;
    }

    public Integer getJuz() {
      return juz;
    }

    public void setJuz(Integer juz) {
      this.juz = juz;
    }

    public Integer getManzil() {
      return manzil;
    }

    public void setManzil(Integer manzil) {
      this.manzil = manzil;
    }

    public Integer getPage() {
      return page;
    }

    public void setPage(Integer page) {
      this.page = page;
    }

    public Integer getRuku() {
      return ruku;
    }

    public void setRuku(Integer ruku) {
      this.ruku = ruku;
    }

    public Integer getHizbQuarter() {
      return hizbQuarter;
    }

    public void setHizbQuarter(Integer hizbQuarter) {
      this.hizbQuarter = hizbQuarter;
    }

    public Boolean getSajda() {
      return sajda;
    }

    public void setSajda(Boolean sajda) {
      this.sajda = sajda;
    }

    public class Edition {

      @SerializedName("identifier")
      @Expose
      private String identifier;
      @SerializedName("language")
      @Expose
      private String language;
      @SerializedName("name")
      @Expose
      private String name;
      @SerializedName("englishName")
      @Expose
      private String englishName;
      @SerializedName("format")
      @Expose
      private String format;
      @SerializedName("type")
      @Expose
      private String type;

      public String getIdentifier() {
        return identifier;
      }

      public void setIdentifier(String identifier) {
        this.identifier = identifier;
      }

      public String getLanguage() {
        return language;
      }

      public void setLanguage(String language) {
        this.language = language;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public String getEnglishName() {
        return englishName;
      }

      public void setEnglishName(String englishName) {
        this.englishName = englishName;
      }

      public String getFormat() {
        return format;
      }

      public void setFormat(String format) {
        this.format = format;
      }

      public String getType() {
        return type;
      }

      public void setType(String type) {
        this.type = type;
      }

    }

    public class Surah {

      @SerializedName("number")
      @Expose
      private Integer number;
      @SerializedName("name")
      @Expose
      private String name;
      @SerializedName("englishName")
      @Expose
      private String englishName;
      @SerializedName("englishNameTranslation")
      @Expose
      private String englishNameTranslation;
      @SerializedName("numberOfAyahs")
      @Expose
      private Integer numberOfAyahs;
      @SerializedName("revelationType")
      @Expose
      private String revelationType;

      public Integer getNumber() {
        return number;
      }

      public void setNumber(Integer number) {
        this.number = number;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public String getEnglishName() {
        return englishName;
      }

      public void setEnglishName(String englishName) {
        this.englishName = englishName;
      }

      public String getEnglishNameTranslation() {
        return englishNameTranslation;
      }

      public void setEnglishNameTranslation(String englishNameTranslation) {
        this.englishNameTranslation = englishNameTranslation;
      }

      public Integer getNumberOfAyahs() {
        return numberOfAyahs;
      }

      public void setNumberOfAyahs(Integer numberOfAyahs) {
        this.numberOfAyahs = numberOfAyahs;
      }

      public String getRevelationType() {
        return revelationType;
      }

      public void setRevelationType(String revelationType) {
        this.revelationType = revelationType;
      }

    }

  }

}
