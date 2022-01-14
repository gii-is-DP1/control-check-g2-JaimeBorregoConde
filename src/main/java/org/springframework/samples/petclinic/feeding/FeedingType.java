package org.springframework.samples.petclinic.feeding;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.pet.PetType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FeedingType extends BaseEntity {
    @Column(name="name",unique=true)
    @Size(min = 5, max = 30)
    @NotNull
    String name;
    @NotNull
    String description;
    @ManyToOne
	@JoinColumn(name = "pet_type_id")
    @NotNull
    PetType petType;
}
