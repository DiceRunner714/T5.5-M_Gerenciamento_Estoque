from django import forms

class EmpresaForm(forms.Form):
    nome = forms.CharField(
        max_length=100,
        label='Nome da Empresa',
        widget=forms.TextInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite o nome da empresa'
        })
    )

class FilialForm(forms.Form):
    nome = forms.CharField(
        max_length=100,
        label='Nome da Filial',
        widget=forms.TextInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite o nome da filial'
        })
    )
    local = forms.CharField(
        max_length=200,
        label='Localização',
        widget=forms.TextInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite a localização'
        })
    )
    empresa_id = forms.IntegerField(
        label='ID da Empresa',
        widget=forms.NumberInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite o ID da empresa'
        })
    )

class ProdutoQuimicoForm(forms.Form):
    nome = forms.CharField(
        max_length=100,
        label='Nome do Produto',
        widget=forms.TextInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite o nome do produto'
        })
    )
    categoria = forms.CharField(
        max_length=100,
        label='Categoria',
        widget=forms.TextInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite a categoria'
        })
    )
    valor = forms.DecimalField(
        max_digits=10,
        decimal_places=2,
        label='Valor',
        widget=forms.NumberInput(attrs={
            'class': 'form-control',
            'step': '0.01',
            'placeholder': 'Digite o valor'
        })
    )
    quantidade = forms.IntegerField(
        label='Quantidade',
        widget=forms.NumberInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite a quantidade'
        })
    )
    toxicidade = forms.IntegerField(
        min_value=1,
        max_value=10,
        label='Toxicidade (1-10)',
        widget=forms.NumberInput(attrs={
            'class': 'form-control',
            'min': '1',
            'max': '10',
            'placeholder': 'Digite o nível de toxicidade'
        })
    )
    filial_id = forms.IntegerField(
        label='ID da Filial',
        widget=forms.NumberInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite o ID da filial'
        })
    )

class FarmaceuticoForm(forms.Form):
    TARJA_CHOICES = [
        ('branca', 'Branca'),
        ('amarela', 'Amarela'),
        ('vermelha', 'Vermelha'),
        ('preta', 'Preta'),
    ]
    
    nome = forms.CharField(
        max_length=100,
        label='Nome do Farmacêutico',
        widget=forms.TextInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite o nome do farmacêutico'
        })
    )
    categoria = forms.CharField(
        max_length=100,
        label='Categoria',
        widget=forms.TextInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite a categoria'
        })
    )
    quantidade = forms.IntegerField(
        label='Quantidade',
        widget=forms.NumberInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite a quantidade'
        })
    )
    valor = forms.DecimalField(
        max_digits=10,
        decimal_places=2,
        label='Valor',
        widget=forms.NumberInput(attrs={
            'class': 'form-control',
            'step': '0.01',
            'placeholder': 'Digite o valor'
        })
    )
    tarja = forms.ChoiceField(
        choices=TARJA_CHOICES,
        label='Tarja',
        widget=forms.Select(attrs={'class': 'form-control'})
    )
    composicao = forms.CharField(
        max_length=500,
        label='Composição',
        widget=forms.Textarea(attrs={
            'class': 'form-control',
            'rows': 3,
            'placeholder': 'Digite a composição'
        })
    )
    retencao_de_receita = forms.BooleanField(
        required=False,
        label='Retenção de Receita',
        widget=forms.CheckboxInput(attrs={'class': 'form-check-input'})
    )
    generico = forms.BooleanField(
        required=False,
        label='Genérico',
        widget=forms.CheckboxInput(attrs={'class': 'form-check-input'})
    )
    receita = forms.BooleanField(
        required=False,
        label='Necessita Receita',
        widget=forms.CheckboxInput(attrs={'class': 'form-check-input'})
    )
    filial_id = forms.IntegerField(
        label='ID da Filial',
        widget=forms.NumberInput(attrs={
            'class': 'form-control',
            'placeholder': 'Digite o ID da filial'
        })
    )