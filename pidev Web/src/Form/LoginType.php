<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;

class LoginType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('email',TextType::class, ['attr'=>['class' => 'form-control input-sm','placeholder'=>"Entrez votre e-mail"]])
        ->add('password',PasswordType::class, ['attr'=>['class' => 'form-control input-sm','placeholder'=>"Entrez votre mot de passe"]])
        ->add('Login', SubmitType::class, ['attr'=>['class' => 'btn btn-log btn-block btn-thm2']])
        
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
