from sqlalchemy import Column, Integer, String, Float, Boolean, ForeignKey
from sqlalchemy.orm import relationship
from config.database import Base

class ProdutoQuimicoORM(Base):
    __tablename__ = "produto_quimico"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String, nullable=False)
    categoria = Column(String, nullable=False)
    valor = Column(Float, nullable=False)
    quantidade = Column(Integer, nullable=False)
    restrito = Column(Boolean, default=False)
    toxicidade = Column(Integer, nullable=False)
    filial_id = Column(Integer, ForeignKey("filial.id"), nullable=False)

    filial = relationship("FilialORM", back_populates="produtos")