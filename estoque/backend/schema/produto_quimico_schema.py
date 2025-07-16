from pydantic import BaseModel
from typing import Optional


class ProdutoQuimicoCreate(BaseModel):
    nome: str
    categoria: str
    valor: float
    quantidade: int
    toxicidade: int
    filial_id: int


class ProdutoQuimicoUpdate(BaseModel):
    nome: Optional[str] = None
    categoria: Optional[str] = None
    quantidade: Optional[int] = None
    valor: Optional[float] = None
    toxicidade: Optional[int] = None


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
