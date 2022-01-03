package com.tqiprojeto.analisecreditoapi.service;

import com.tqiprojeto.analisecreditoapi.entity.Emprestimo;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoDataPrimeiraParcelaExcedida;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoNaoCadastradoException;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoParcelasExcedida;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRepository;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRetornoSimples;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private EmprestimoRepository emprestimoRepository;

    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Emprestimo> listarTodosEmprestimos (){
        return emprestimoRepository.findAll();
    }


    public List<EmprestimoRetornoSimples> listarSimples(Integer id) throws EmprestimoNaoCadastradoException {

        List<EmprestimoRetornoSimples> listaSimples = emprestimoRepository.listaSimples(id);
        return listaSimples;

    }

   public List<Emprestimo> listarEmprestimoDetalhado (Integer id) throws EmprestimoNaoCadastradoException {

        return emprestimoRepository.listarEmprestimoDetalhado(id);
    }

       public Emprestimo buscarPorId(Integer id) throws EmprestimoNaoCadastradoException {

        Emprestimo emprestimo = emprestimoRepository.findById(id).orElseThrow(() -> new EmprestimoNaoCadastradoException(id));
        return emprestimo ;
    }

    public Emprestimo solicitar(Emprestimo emprestimo) throws EmprestimoParcelasExcedida, ParseException, EmprestimoDataPrimeiraParcelaExcedida {

        LocalDate dataMaxima = LocalDate.now().plusMonths(3);

        if (emprestimo.getQuantidadeParcelas() > 60) {

                throw new EmprestimoParcelasExcedida(emprestimo.getQuantidadeParcelas());

             }else if (emprestimo.getDataPrimeiraParcela().isAfter(dataMaxima)) {

            throw new EmprestimoDataPrimeiraParcelaExcedida();

        }else return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo atualizar(Integer id, Emprestimo emprestimo) throws EmprestimoNaoCadastradoException {

        emprestimoRepository.findById(id).orElseThrow(() -> new EmprestimoNaoCadastradoException(id));
        emprestimo.setId(id);
        return emprestimoRepository.save(emprestimo);
    }

    public void deletar(Integer id) throws EmprestimoNaoCadastradoException{
        emprestimoRepository.deleteById(id);
    }

}
