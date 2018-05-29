package com.suyang;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * Builds instances of type {@link Person Person}.
 * Initialize attributes and then invoke the {@link #build()} method to create an
 * immutable instance.
 * <p><em>{@code PersonBuilder} is not thread-safe and generally should not be stored in a field or collection,
 * but instead used immediately to create instances.</em>
 */
@SuppressWarnings({"all"})
@Generated("org.immutables.processor.ProxyProcessor")
@org.immutables.value.Generated(from = "Person", generator = "Immutables")
public final class PersonBuilder {
  private static final long INIT_BIT_NAME = 0x1L;
  private static final long INIT_BIT_ADDRESS = 0x2L;
  private long initBits = 0x3L;

  private String name;
  private String address;

  /**
   * Creates a builder for {@link Person Person} instances.
   */
  public PersonBuilder() {
  }

  /**
   * Fill a builder with attribute values from the provided {@code Person} instance.
   * Regular attribute values will be replaced with those from the given instance.
   * Absent optional values will not replace present values.
   * @param instance The instance from which to copy values
   * @return {@code this} builder for use in a chained invocation
   */
  public final PersonBuilder from(Person instance) {
    Objects.requireNonNull(instance, "instance");
    name(instance.getName());
    address(instance.getAddress());
    return this;
  }

  /**
   * Initializes the value for the {@link Person#getName() name} attribute.
   * @param name The value for name 
   * @return {@code this} builder for use in a chained invocation
   */
  public final PersonBuilder name(String name) {
    this.name = Objects.requireNonNull(name, "name");
    initBits &= ~INIT_BIT_NAME;
    return this;
  }

  /**
   * Initializes the value for the {@link Person#getAddress() address} attribute.
   * @param address The value for address 
   * @return {@code this} builder for use in a chained invocation
   */
  public final PersonBuilder address(String address) {
    this.address = Objects.requireNonNull(address, "address");
    initBits &= ~INIT_BIT_ADDRESS;
    return this;
  }

  /**
   * Builds a new {@link Person Person}.
   * @return An immutable instance of Person
   * @throws java.lang.IllegalStateException if any required attributes are missing
   */
  public Person build() {
    if (initBits != 0) {
      throw new IllegalStateException(formatRequiredAttributesMessage());
    }
    return new PersonBuilder.ImmutablePerson(this);
  }

  private String formatRequiredAttributesMessage() {
    List<String> attributes = new ArrayList<>();
    if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
    if ((initBits & INIT_BIT_ADDRESS) != 0) attributes.add("address");
    return "Cannot build Person, some of required attributes are not set " + attributes;
  }

  /**
   * Immutable implementation of {@link Person}.
   * <p>
   * Use the builder to create immutable instances:
   * {@code new PersonBuilder()}.
   */
  private static final class ImmutablePerson implements Person {
    private final String name;
    private final String address;

    private ImmutablePerson(PersonBuilder builder) {
      this.name = builder.name;
      this.address = builder.address;
    }

    /**
     * @return The value of the {@code name} attribute
     */
    @Override
    public String getName() {
      return name;
    }

    /**
     * @return The value of the {@code address} attribute
     */
    @Override
    public String getAddress() {
      return address;
    }

    /**
     * This instance is equal to all instances of {@code ImmutablePerson} that have equal attribute values.
     * @return {@code true} if {@code this} is equal to {@code another} instance
     */
    @Override
    public boolean equals(Object another) {
      if (this == another) return true;
      return another instanceof PersonBuilder.ImmutablePerson
          && equalTo((PersonBuilder.ImmutablePerson) another);
    }

    private boolean equalTo(PersonBuilder.ImmutablePerson another) {
      return name.equals(another.name)
          && address.equals(another.address);
    }

    /**
     * Computes a hash code from attributes: {@code name}, {@code address}.
     * @return hashCode value
     */
    @Override
    public int hashCode() {
      int h = 5381;
      h += (h << 5) + name.hashCode();
      h += (h << 5) + address.hashCode();
      return h;
    }

    /**
     * Prints the immutable value {@code Person} with attribute values.
     * @return A string representation of the value
     */
    @Override
    public String toString() {
      return "Person{"
          + "name=" + name
          + ", address=" + address
          + "}";
    }
  }
}
