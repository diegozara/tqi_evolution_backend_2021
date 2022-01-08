package com.tqiprojeto.analisecreditoapi.controller;

import com.tqiprojeto.analisecreditoapi.entity.Emprestimo;
import com.tqiprojeto.analisecreditoapi.exception.ClienteNaoCadastradoException;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoDataPrimeiraParcelaExcedida;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoNaoCadastradoException;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoParcelasExcedida;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRetornoDetalhado;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRetornoSimples;
import com.tqiprojeto.analisecreditoapi.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emprestimos")
public class EmprestimoController {

    private EmprestimoService emprestimoService;

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    //listar todos emprestimos cadastrados
    @GetMapping
    public List<Emprestimo> listarTodosEmprestimos(){
        return emprestimoService.listarTodosEmprestimos();
    }

    //lista simples dos campos de todos os emprestimos de um cliente especificado por id
    @GetMapping("/clientes/{id}")
    public List<EmprestimoRetornoSimples> listarEmprestimos(@PathVariable Integer id) throws EmprestimoNaoCadastradoException {

        return emprestimoService.listarSimples(id);
    }

    //lista completa dos campos de todos os emprestimos de um cliente especificado por id
    @GetMapping("/clientes/detalhado/{id}")
    public List<EmprestimoRetornoDetalhado> listarEmprestimoDetalhado (@PathVariable Integer id) throws  EmprestimoNaoCadastradoException{
        return emprestimoService.listarEmprestimoDetalhado(id);
    }

    //listar emprestimo cadastrado por id
    @GetMapping("/{id}")
    public Emprestimo buscarPorId(@PathVariable Integer id) throws EmprestimoNaoCadastradoException {
        return emprestimoService.buscarPorId(id);
    }

    //cadastrar emprestimo
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Emprestimo solicitar (@RequestBody Emprestimo emprestimo ) throws EmprestimoParcelasExcedida, ParseException, EmprestimoDataPrimeiraParcelaExcedida, ClienteNaoCadastradoException {
        return emprestimoService.solicitar(emprestimo);
    }

    //atualizar emprestimo cadastrado
    @PutMapping ("/{id}")
    public Emprestimo atualizar (@PathVariable Integer id, @RequestBody Emprestimo emprestimo) throws EmprestimoNaoCadastradoException {
        return emprestimoService.atualizar(id,emprestimo);
    }

    //deletar emprestimo por id
    @DeleteMapping ("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar (@PathVariable Integer id) throws EmprestimoNaoCadastradoException{
        emprestimoService.deletar(id);
    }

}
