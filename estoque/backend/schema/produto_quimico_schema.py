from pydantic import BaseModel


class ProdutoQuimicoCreate(BaseModel):
    nome: str
    categoria: str
    valor: float
    quantidade: int
    toxicidade: int
    filial_id: int


class ProdutoQuimicoResponse(BaseModel):
    id: int
    nome: str
    categoria: str
    valor: float
    quantidade: int
    toxicidade: int
    restrito: bool
    filial_id: int

    class Config:
        from_attributes = True
