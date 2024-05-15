package edu.ucan.bilheteira.programacao;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProgramacaoService {
    @Autowired
    private ProgramacaoRepository programacaoRepository;

    @Transactional
    public Programacao create(Programacao programacao){
        programacao.setEstado(true);
        LocalDate dataViagem = programacao.getDataViagem();
        DayOfWeek dayOfWeek = dataViagem.getDayOfWeek();
        String diaSemana = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
        programacao.setDiaSemana(diaSemana);

        return this.programacaoRepository.save(programacao);
    }

    public Programacao findById(UUID pkProgramacao){
        Optional<Programacao> programacaoOpt = programacaoRepository.findById(pkProgramacao);
        return programacaoOpt.orElseThrow(() -> new CustomEntityNotFoundException("programacao não encontrada com o id: " + pkProgramacao));
    }

    @Transactional
    public Programacao update(Programacao newProgramacao, UUID pkProgramacao){
        Programacao programacao = findById(pkProgramacao);
        LocalDateTime createAtOriginal = programacao.getDataCadastro();
        programacao.setEstado(true);
        programacao.setDataCadastro(createAtOriginal);
        programacao.setFkRota(newProgramacao.getFkRota() != null ? newProgramacao.getFkRota() : programacao.getFkRota());
        programacao.setFkTransporte(newProgramacao.getFkTransporte() != null ? newProgramacao.getFkTransporte() : programacao.getFkTransporte());

        LocalDate dataViagem = newProgramacao.getDataViagem();
        DayOfWeek dayOfWeek = dataViagem.getDayOfWeek();
        String diaSemana = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());

        programacao.setDiaSemana(diaSemana);
        programacao.setDataViagem(newProgramacao.getDataViagem());
        programacao.setHoraChegada(newProgramacao.getHoraChegada());
        programacao.setHoraPartida(newProgramacao.getHoraPartida());
        return this.programacaoRepository.save(programacao);
    }

    public void delete(UUID pkProgramacao){
        Programacao programacao = findById(pkProgramacao);
        programacao.setEstado(false);
        this.programacaoRepository.save(programacao);
    }

    public List<Programacao> findAll(){
        List<Programacao> programacoes = this.programacaoRepository.findByEstadoTrueOrderByDataViagemAsc();
        if (programacoes.isEmpty()){
            throw new CustomEntityNotFoundException("A lista de programações activadas está vazia");
        }
        return programacoes;
    }

    public List<Programacao> findByRotaAndDataViagem(UUID pkRota, LocalDate dataViagem){
        List<Programacao> programacoes = this.programacaoRepository.findByRotaAndDataViagem(pkRota,dataViagem);
        if (programacoes.isEmpty()){
            throw new CustomEntityNotFoundException("Não existe programação para esta data");
        }
        return programacoes;
    }

}
