package br.edu.infnet.matheusmacielapi.service;

import java.util.Collection;

public interface CrudService<T, ID> {

    T salvar(T entidade);
    T buscarPorId(ID id);
    void excluir(ID id);
    Collection<T> listarTodos();
    T atualizar(ID id, T entidade);

}
