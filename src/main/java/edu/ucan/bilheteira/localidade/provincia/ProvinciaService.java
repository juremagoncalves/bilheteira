package edu.ucan.bilheteira.localidade.provincia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public void saveProvincia(MultipartFile file)
    {
        Provincia provincia = new Provincia();
        if (!ExcelUploadService.isValidExcelFile(file))
        {
            throw new IllegalArgumentException("O arquivo não é um arquivo Excel válido");
        }
        try{
            provinciaRepository.deleteAll();
            List<Provincia> provList = ExcelUploadService.getProvinciaDataFromExcel(file.getInputStream());

            this.provinciaRepository.saveAll(provList);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("O arquivo não é válido");
        }
    }

    public List<Provincia> findAll(){
        return this.provinciaRepository.findByEstadoTrueOrderByDesignacaoAsc();
    }
}
