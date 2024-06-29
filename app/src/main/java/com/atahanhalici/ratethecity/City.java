package com.atahanhalici.ratethecity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class City implements Serializable {
    List<String> images;
    String adi;
    String ulke;
    String yazar;
    Integer oySayisi;
    String ozet;
    String tarihvekultur;
    String dogalguzellikler;
    String mutfak;
    String ekonomiveturizm;
    String yazaryorumu;
    Double tarihPuan;
    Double DogalGuzellikPuan;
    Double mutfakPuan;
    Double ekonomiPuan;
    Double ortalamaPuan;
    String valilikLink;
    String belediyeLink;


   public City( String adi,
           String ulke,
                List<String> images,
           String yazar,
           Integer oySayisi,
           String ozet,
           String tarihvekultur,
           String dogalguzellikler,
           String mutfak,
           String ekonomiveturizm,
           String yazaryorumu,
           Double tarihPuan,
           Double DogalGuzellikPuan,
           Double mutfakPuan,
           Double ekonomiPuan,
           Double ortalamaPuan, String valilikLink,
                        String belediyeLink){
       this.images=images;
       this.adi=adi;
       this.ulke=ulke;
       this.yazar=yazar;
       this.oySayisi=oySayisi;
       this.ozet=ozet;
       this.tarihvekultur=tarihvekultur;
       this.dogalguzellikler=dogalguzellikler;
       this.mutfak=mutfak;
       this.ekonomiveturizm=ekonomiveturizm;
       this.yazaryorumu=yazaryorumu;
       this.tarihPuan=tarihPuan;
       this.DogalGuzellikPuan=DogalGuzellikPuan;
       this.mutfakPuan=mutfakPuan;
       this.ekonomiPuan=ekonomiPuan;
       this.ortalamaPuan=ortalamaPuan;
       this.valilikLink=valilikLink;
       this.belediyeLink=belediyeLink;
    }

    public String getCityName() {
        return this.adi;
    }

    public String getCountryName() {
        return this.ulke;
    }

    public List<String> getImages() {
        return this.images;
    }

    public String getUsername() {
        return this.yazar;
    }

    public Integer getNumberOfVote() {
        return this.oySayisi;
    }

    public String getSummary() {
        return this.ozet;
    }

    public String getCultureDate() {
        return this.tarihvekultur;
    }

    public String getNaturalBeauty() {
        return this.dogalguzellikler;
    }

    public String getCuisine() {
        return this.mutfak;
    }

    public String getEconomyTourism() {
        return this.ekonomiveturizm;
    }

    public String getAuthorComment() {
        return this.yazaryorumu;
    }

    public Double getDateScore() {
        return this.tarihPuan;
    }

    public Double getBeautyScore() {
        return this.DogalGuzellikPuan;
    }

    public Double getCuisineScore() {
        return this.mutfakPuan;
    }

    public Double getEconomyScore() {
        return this.ekonomiPuan;
    }

    public Double getAuthorScore() {
        return this.ortalamaPuan;
    }

    public String getValilikLink() {
        return this.valilikLink;
    }

    public String getBelediyeLink() {
        return this.belediyeLink;
    }

    /*@Override
    public String toString() {
        return "City{" +
                "images=" + Arrays.toString(images) +
                ", adi='" + adi + '\'' +
                ", ulke='" + ulke + '\'' +
                ", yazar='" + yazar + '\'' +
                ", oySayisi=" + oySayisi +
                ", ozet='" + ozet + '\'' +
                ", tarihvekultur='" + tarihvekultur + '\'' +
                ", dogalguzellikler='" + dogalguzellikler + '\'' +
                ", mutfak='" + mutfak + '\'' +
                ", ekonomiveturizm='" + ekonomiveturizm + '\'' +
                ", yazaryorumu='" + yazaryorumu + '\'' +
                ", tarihPuan=" + tarihPuan +
                ", DogalGuzellikPuan=" + DogalGuzellikPuan +
                ", mutfakPuan=" + mutfakPuan +
                ", ekonomiPuan=" + ekonomiPuan +
                ", ortalamaPuan=" + ortalamaPuan +
                ", valilikLink='" + valilikLink + '\'' +
                ", belediyeLink='" + belediyeLink + '\'' +
                '}';
    }*/

}
