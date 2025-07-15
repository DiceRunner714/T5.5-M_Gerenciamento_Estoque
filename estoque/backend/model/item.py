from abc import ABC  # , abstractmethod


class Item(ABC):
    def __init__(self, nome: str, categoria: str, quantidade: int, id: int, valor: float, restrito: bool = False):
        self._nome = nome
        self._categoria = categoria,
        self._quantidade = quantidade
        self._id = id
        self._valor = valor
        self._restrito = restrito

    def restringir(self) -> None:
        self._restrito = True

    def liberar(self) -> None:
        self._restrito = False

    def listar_caracteristicas_basicas(self) -> str:
        return self._nome + self._categoria + str(self._quantidade) + str(self._id) + str(self._valor) + str(self._restrito)
