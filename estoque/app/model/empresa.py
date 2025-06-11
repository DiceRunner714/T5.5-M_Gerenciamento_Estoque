from model.filial import Filial

class Empresa:
    def __init__(self, nome: str):
        self._nome: str = nome
        self._filiais: list[Filial] = []

    def adicionar_filial(self, nome: str, local: str, id: int) -> None:
        nova_filial = Filial(nome, local, id)
        self._filiais.append(nova_filial)

    def buscar_filial(self, id: int) -> Filial | None:
        for filial in self._filiais:
            if filial.id == id:
                return filial
        return None
    
    def remover_filial(self, id: int) -> None:
        self._filiais = [f for f in self._filiais if f.id != id]

    def atualizar_filial(self, nome: str, local: str, novo_id: int, filial_id: int) -> None:
        filial = self.buscar_filial(filial_id)
        if filial:
            filial.nome = nome
            filial.local = local
            filial.id = novo_id