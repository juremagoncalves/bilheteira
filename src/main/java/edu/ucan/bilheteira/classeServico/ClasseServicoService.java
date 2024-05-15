package edu.ucan.bilheteira.classeServico;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClasseServicoService {

    @Autowired
    private ClasseServicoRepository classeServicoRepository;
    @Transactional
    public ClasseServico create(ClasseServico classeServico){
        classeServico.setEstado(true);
        return classeServicoRepository.save(classeServico);
    }

    public ClasseServico findById(UUID pkClasseServico){
        Optional<ClasseServico>  classeServicoOpt = this.classeServicoRepository.findById(pkClasseServico);
        return classeServicoOpt.orElseThrow( () -> new CustomEntityNotFoundException("Classe de serviço não encontrada com o id " +pkClasseServico ));
    }
    @Transactional
    public ClasseServico update(ClasseServico newClasseServico, UUID pkClasseServico){
        ClasseServico classeServico = findById(pkClasseServico);
        LocalDateTime createAtOriginal = classeServico.getDataCadastro();
        classeServico.setDesignacao(newClasseServico.getDesignacao());
        classeServico.setEstado(true);
        classeServico.setDataCadastro(createAtOriginal);
        return classeServicoRepository.save(classeServico);
    }

    public void delete(UUID pkClasseServico){
        ClasseServico classeServico = findById(pkClasseServico);
        classeServico.setEstado(false);
        this.classeServicoRepository.save(classeServico);
    }

    public List<ClasseServico> findByEstadoTrueOrderByDesignationAsc(){
        List<ClasseServico> classeServicos = this.classeServicoRepository.findByEstadoTrueOrderByDesignacaoAsc();
        if(classeServicos.isEmpty()){
            throw new CustomEntityNotFoundException("Lista de classes de serviços activadas está vazia");
        }
        return classeServicos;
    }

}
