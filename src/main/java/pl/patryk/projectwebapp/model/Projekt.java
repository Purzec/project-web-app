package pl.patryk.projectwebapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Projekt {

    private Integer projektId;

    @NotBlank(message = "Pole nazwa nie może być puste!")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków!")

    private String nazwa;
    private String opis;
    //private LocalDateTime dataCzasUtworzenia;
    //private LocalDate dataCzasModyfikacji;

    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    private LocalDate dataCzasUtworzenia;
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    private LocalDate dataCzasModyfikacji;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataOddania;
    @JsonIgnoreProperties({"projekt"})
    private List<Zadanie> zadania;
    private Set<Student> studenci;

    public Projekt() {
    }

    public Projekt(Integer i, String nazwa1, String opis1, LocalDate now, LocalDate of) {
        this.projektId=i;
        this.nazwa=nazwa1;
        this.opis=opis1;
        this.dataCzasUtworzenia=now;
        this.dataCzasModyfikacji=of;
    }



    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Set<Student> getStudenci() {
        return studenci;
    }

    public void setStudenci(Set<Student> studenci) {
        this.studenci = studenci;
    }

    public Integer getProjektId() {
        return projektId;
    }

    public void setProjektId(Integer projektId) {
        this.projektId = projektId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public LocalDate getDataCzasUtworzenia() {
        return dataCzasUtworzenia;
    }

    public void setDataCzasUtworzenia(LocalDate dataCzasUtworzenia) {
        this.dataCzasUtworzenia = dataCzasUtworzenia;
    }

    public LocalDate getDataOddania() {
        return dataOddania;
    }

    public void setDataOddania(LocalDate dataOddania) {
        this.dataOddania = dataOddania;
    }

    public LocalDate getDataCzasModyfikacji() {
        return dataCzasModyfikacji;
    }

    public void setDataCzasModyfikacji(LocalDate dataCzasModyfikacji) {
        this.dataCzasModyfikacji = dataCzasModyfikacji;
    }

    public List<Zadanie> getZadania() {
        return zadania;
    }

    public void setZadania(List<Zadanie> zadania) {
        this.zadania = zadania;
    }
}