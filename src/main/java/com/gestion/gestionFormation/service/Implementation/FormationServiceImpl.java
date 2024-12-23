package com.gestion.gestionFormation.service.Implementation;

import com.gestion.gestionFormation.exception.ClasseNotFoundException;
import com.gestion.gestionFormation.exception.CourseNotFoundException;
import com.gestion.gestionFormation.model.Formation;
import com.gestion.gestionFormation.repository.FormationRepository;
import com.gestion.gestionFormation.service.FormationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FormationServiceImpl implements FormationService {
    private final FormationRepository formationRepository;
    @Override
    public Formation addFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public Formation updateFormation(Long id, Formation formation) {
        return formationRepository.findById(id)
                .map(existingFormation -> {
                    existingFormation.setTitre(formation.getTitre());
                    existingFormation.setNiveau(formation.getNiveau());
                    existingFormation.setPrerequis(formation.getPrerequis());
                    existingFormation.setCapaciteMin(formation.getCapaciteMin());
                    existingFormation.setCapaciteMax(formation.getCapaciteMax());
                    existingFormation.setDateDebut(formation.getDateDebut());
                    existingFormation.setDateFin(formation.getDateFin());
                    existingFormation.setStatut(formation.getStatut());
                    return formationRepository.save(existingFormation);
                })
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Override
    public Optional<Formation> getFormation(Long id) {
        return Optional.ofNullable(formationRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id)));
    }

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    @Override
    public String deleteFormation(Long id) {
        if (!formationRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        formationRepository.deleteById(id);
        return "Formation Supprimée";
    }
}
