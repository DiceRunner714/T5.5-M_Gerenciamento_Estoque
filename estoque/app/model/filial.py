from model.item import Item


class Filial:
    def __init__(self, nome: str, local: str, id: int):
        self._nome: str = nome
        self._local: str = local
        self._id: int = id
        self._itens: list[Item] = []

    def adicionar_item(self, item: Item) -> None:
        self._itens.append(item)

    def remover_item(self, item: Item) -> None:
        if item in self._itens:
            self._itens.remove(item)

    def listar_caracteristicas(self) -> str:
        return f"ID: {self._id}\nNome: {self._nome}\nLocal: {self._local}\nQtd Itens: {len(self._itens)}"

    @property
    def itens(self) -> list[Item]:
        return self._itens.copy()

    @property
    def nome(self) -> str:
        return self._nome

    @nome.setter
    def nome(self, nome: str) -> None:
        self._nome = nome

    @property
    def local(self) -> str:
        return self._local

    @local.setter
    def local(self, local: str) -> None:
        self._local = local

    @property
    def id(self) -> int:
        return self._id

    @id.setter
    def id(self, id: int) -> None:
        self._id = id

    def __str__(self) -> str:
        return f"Filial {self._nome} (ID: {self._id}) - Local: {self._local}"
